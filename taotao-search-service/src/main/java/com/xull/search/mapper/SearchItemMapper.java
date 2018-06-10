package com.xull.search.mapper;

import java.util.List;

import com.xull.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	List<SearchItem> selectItemList();
	
	SearchItem selectItemById(long itemId);
}
