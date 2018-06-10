package com.xull.search.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:resource/solr.properties"})
public class SolrConfig {
	
	@Value("${SOLR_BASEURL}")
	private String SOLR_BASEURL;

	@Bean
	public SolrServer solrServer(){
		return new HttpSolrServer(SOLR_BASEURL);
	}
}
