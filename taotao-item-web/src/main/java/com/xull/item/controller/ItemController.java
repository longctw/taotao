package com.xull.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.manager.service.ItemService;
import com.xull.pojo.TbItem;
import com.xull.pojo.TbItemDesc;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("{itemId}")
	public String getItemById(@PathVariable long itemId, Model model){
		TbItem tbItem = itemService.getItemById(itemId);
		TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
		
		model.addAttribute("item", tbItem);
		model.addAttribute("itemDesc", tbItemDesc);
		
		return "item";
	}
}
