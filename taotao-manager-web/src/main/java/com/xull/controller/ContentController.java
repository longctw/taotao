package com.xull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.content.service.ContentService;
import com.xull.pojo.TbContent;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIResult contentListByCat(long categoryId, int page, int rows){
		return contentService.contentListByCat(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content){
		return contentService.saveContent(content);
	}
}
