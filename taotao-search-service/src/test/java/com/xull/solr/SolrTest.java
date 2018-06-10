package com.xull.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void test1() throws Exception{
		// 第一步：把solrJ的jar包添加到工程中。
		// 第二步：创建一个SolrServer，使用HttpSolrServer创建对象。
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.107:8180/solr");
		// 第三步：创建一个文档对象SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 第四步：向文档中添加域。必须有id域，域的名称必须在schema.xml中定义。
		document.addField("id", "test001");
		document.addField("item_title", "测试商品");
		document.addField("item_price", "199");
		// 第五步：把文档添加到索引库中。
		solrServer.add(document);
		// 第六步：提交。
		solrServer.commit();
	}
	
	@Test
	public void test2() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.107:8180/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	@Test
	public void test3 () throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://192.168.66.107:8180/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = solrServer.query(query);
		SolrDocumentList solrDocumentList = response.getResults();
		
		System.out.println("查询结果的总记录数：" + solrDocumentList.getNumFound());
		
		for(SolrDocument doc : solrDocumentList){
			System.out.println(doc.get("id"));
			System.out.println(doc.get("item_title"));
			System.out.println(doc.get("item_price"));

		}
	}
}
