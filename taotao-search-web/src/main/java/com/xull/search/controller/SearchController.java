package com.xull.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xull.common.pojo.SearchResult;
import com.xull.search.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString, 
			@RequestParam(defaultValue="1")Integer page, Model model) throws Exception {

		SearchResult result = searchService.search(queryString, page, ITEM_ROWS);
		//传递给页面
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);
		
		//返回逻辑视图
		return "search";
	}
}
