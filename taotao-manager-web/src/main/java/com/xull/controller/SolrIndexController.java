package com.xull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xull.common.pojo.TaotaoResult;
import com.xull.search.service.SearchService;

@Controller
@RequestMapping("/index")
public class SolrIndexController {

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAllIndex(){
		return searchService.importAllIndex();
	}
}
