package com.xull.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.EasyUITreeNode;
import com.xull.common.pojo.TaotaoResult;
import com.xull.manager.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> itemList(@RequestParam(value="id", defaultValue="0") int parentId){
		List<EasyUITreeNode> result = itemCatService.getItemCatByParentId(parentId);
		return result;
	}
}
