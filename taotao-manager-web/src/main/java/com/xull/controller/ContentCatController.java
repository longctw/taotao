package com.xull.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.EasyUITreeNode;
import com.xull.common.pojo.TaotaoResult;
import com.xull.content.service.ContentCatService;

@Controller
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatInfo(@RequestParam(value="id", defaultValue="0") long parentId){
		return contentCatService.getContentCatList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addNode(String name, long parentId){
		return contentCatService.createContentCat(name, parentId);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteNode(long id){
		return contentCatService.deleteContentCat(id);
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateNode(String name, long id){
		return contentCatService.editContentCat(id, name);
	}
}
