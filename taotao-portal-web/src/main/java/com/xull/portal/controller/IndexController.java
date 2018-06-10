package com.xull.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xull.common.utils.JsonUtils;
import com.xull.content.service.ContentService;
import com.xull.pojo.TbContent;
import com.xull.portal.pojo.Ad1Node;

@Controller
public class IndexController {
	
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CID;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		//取轮播图的内容信息
		List<TbContent> contentList = contentService.contentListByCid(AD1_CID);
		//转换成Ad1NodeList
		List<Ad1Node> ad1List = new ArrayList<>();
		for (TbContent tbContent : contentList) {
			Ad1Node node = new Ad1Node();
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setHref(tbContent.getUrl());
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			//添加到列表
			ad1List.add(node);
		}
		//把数据传递给页面。
		model.addAttribute("ad1", JsonUtils.objectToJson(ad1List));
		return "index";
	}
	
	@RequestMapping("{page}")
	public String index(@PathVariable String page){
		return page;
	}

}
