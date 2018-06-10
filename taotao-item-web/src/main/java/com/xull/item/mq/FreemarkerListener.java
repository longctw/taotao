package com.xull.item.mq;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.xull.manager.service.ItemService;
import com.xull.pojo.TbItem;
import com.xull.pojo.TbItemDesc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class FreemarkerListener {
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Autowired
	private ItemService itemService;
	
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@JmsListener(destination = "MQ_TOPIC_ITEM_ADD")
	public void itemAddTopicProcess(long itemId){
		System.out.println(itemId+">>>>>>>>>>>>>>>>>>>>生成静态化页面<<<<<<<<<<<<<<<<<<<<<");
		
		try {
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.ftl");
			TbItem tbItem = itemService.getItemById(itemId);
			TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("item", tbItem);
			paramMap.put("itemDesc", tbItemDesc);
			
			Writer out = new FileWriter(new File(HTML_OUT_PATH + itemId + ".html"));
			System.out.println("静态页面已生成：" + HTML_OUT_PATH + itemId + ".html");
			template.process(paramMap, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
