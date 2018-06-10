package com.xull.search.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.xull.common.pojo.SearchItem;
import com.xull.search.mapper.SearchItemMapper;
import com.xull.search.service.SearchService;

@Component
public class SearchListener {
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SearchService searchService;
	
	@JmsListener(destination = "MQ_TOPIC_ITEM_ADD")
	public void receiveTopic(long itemId){
		System.out.println(itemId+">>>>>>>>>>>>>>>>>>>>>>>>>同步索引库<<<<<<<<<<<<<<<<<<<<<<<<<");
		SearchItem item = searchItemMapper.selectItemById(itemId);
		searchService.importItemIndex(item);
	}
}
