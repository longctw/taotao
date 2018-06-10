package com.xull.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xull.common.pojo.SearchItem;
import com.xull.common.pojo.SearchResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.search.dao.SearchDao;
import com.xull.search.mapper.SearchItemMapper;
import com.xull.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public TaotaoResult importAllIndex() {
		
		try {
			List<SearchItem> itemList = searchItemMapper.selectItemList();
			for(SearchItem item : itemList){
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", item.getId());
				document.addField("item_title", item.getTitle());
				document.addField("item_sell_point", item.getSell_point());
				document.addField("item_price", item.getPrice());
				document.addField("item_image", item.getImage());
				document.addField("item_category_name", item.getCategory_name());
				document.addField("item_desc", item.getItem_desc());
				// 5、向索引库中添加文档。
				solrServer.add(document);
			}
			solrServer.commit();
		} catch (Exception e) {
			TaotaoResult.build(500, "import solrData error!");
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}
	
	@Override
	public TaotaoResult importItemIndex(SearchItem item) {
		
		try {
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			document.addField("item_desc", item.getItem_desc());
			// 5、向索引库中添加文档。
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			TaotaoResult.build(500, "import solrData error!");
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public SearchResult search(String queryString, int page, int rows) throws SolrServerException {
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		// 3、设置分页条件
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		// 4、需要指定默认搜索域。
		query.set("df", "item_title");
		// 5、设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		// 6、执行查询，调用SearchDao。得到SearchResult
		SearchResult result = searchDao.search(query);
		// 7、需要计算总页数。
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		// 8、返回SearchResult
		return result;
	}

}
