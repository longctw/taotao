package com.xull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.manager.service.ItemService;
import com.xull.pojo.TbItem;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		return itemService.getItemById(itemId);
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIResult itemList(int page, int rows){
		EasyUIResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc) {
		TaotaoResult result = itemService.saveItem(item, desc);
		return result;
	}

}
