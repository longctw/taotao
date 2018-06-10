package com.xull.search.service;

import com.xull.common.pojo.SearchItem;
import com.xull.common.pojo.SearchResult;
import com.xull.common.pojo.TaotaoResult;

public interface SearchService {

	public TaotaoResult importAllIndex();
	
	public TaotaoResult importItemIndex(SearchItem item);
	
	public SearchResult search(String queryString, int page, int rows) throws Exception;
}
