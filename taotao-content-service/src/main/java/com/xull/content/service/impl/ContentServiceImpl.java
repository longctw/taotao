package com.xull.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.common.utils.JsonUtils;
import com.xull.content.jedis.JedisClient;
import com.xull.content.service.ContentService;
import com.xull.mapper.TbContentMapper;
import com.xull.pojo.TbContent;
import com.xull.pojo.TbContentExample;
import com.xull.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Value("${CONTENT_LIST_KEY}")
	private String CONTENT_LIST_KEY;
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public EasyUIResult contentListByCat(long categoryId, int page, int row) {
		//PageHelper.startPage(page, row);
		//TODO 
		/**
		 * 问题待解决
		 * 
		 * 问题描述
		 * 开启分页后，按条件查询功能异常
		 * [ERROR INFO] There is no getter for property named '__frch_criterion_1' in 'class com.xull.pojo.TbContentExample'
		 */
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//PageInfo<TbContent> info = new PageInfo<TbContent>(list);
		
		EasyUIResult result = new EasyUIResult(list.size(), list);
		return result;
	}

	@Override
	public TaotaoResult saveContent(TbContent content) {
		Date currentDate = new Date();
		content.setCreated(currentDate);
		content.setUpdated(currentDate);
		contentMapper.insert(content);
		
		//向缓存中添加数据
		try {
			System.out.println("删除redis中保存内容列表信息，分类ID："+content.getCategoryId());
			jedisClient.hdel(CONTENT_LIST_KEY, content.getCategoryId() + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok(content);
	}

	@Override
	public List<TbContent> contentListByCid(long cid) {
		
		//从缓存中查询数据
		try {
			System.out.println("从redis中查询内容列表信息，分类ID："+cid);
			String redisListInfo = jedisClient.hget(CONTENT_LIST_KEY, cid + "");
			if (!StringUtils.isEmpty(redisListInfo)){
				System.out.println("从redis中查询内容列表信息成功，分类ID："+cid);
				List<TbContent> listFromRedis = JsonUtils.jsonToList(redisListInfo, TbContent.class);
				return listFromRedis;
			}
			System.out.println("从redis中查询内容列表信息失败，分类ID："+cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		//向缓存中添加数据
		try {
			System.out.println("向redis中保存内容列表信息，分类ID："+cid);
			jedisClient.hset(CONTENT_LIST_KEY, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
