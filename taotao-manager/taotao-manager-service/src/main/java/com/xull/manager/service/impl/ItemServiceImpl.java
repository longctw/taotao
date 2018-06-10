package com.xull.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.common.utils.JsonUtils;
import com.xull.manager.jedis.JedisClient;
import com.xull.manager.mq.Producer;
import com.xull.manager.service.ItemService;
import com.xull.manager.utils.IDUtils;
import com.xull.mapper.TbItemDescMapper;
import com.xull.mapper.TbItemMapper;
import com.xull.pojo.TbContent;
import com.xull.pojo.TbItem;
import com.xull.pojo.TbItemDesc;
import com.xull.pojo.TbItemExample;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Value("${MQ_TOPIC_ITEM_ADD}")
	private String MQ_TOPIC_ITEM_ADD;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Autowired
	private Producer producer;

	@Override
	public TbItem getItemById(long itemId) {
		
		//从缓存中查询数据
		try {
			System.out.println("从redis中查询商品信息，商品ID："+itemId);
			String redisItemInfo = jedisClient.get("ITEM_INFO:"+itemId+":BASE");
			if (!StringUtils.isEmpty(redisItemInfo)){
				System.out.println("从redis中查询商品信息成功，商品ID："+itemId);
				TbItem item = JsonUtils.jsonToPojo(redisItemInfo, TbItem.class);
				return item;
			}
			System.out.println("从redis中查询商品信息失败，商品ID："+itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		
		//向缓存中添加数据
		try {
			System.out.println("向redis中保存商品信息，商品ID："+itemId);
			jedisClient.set("ITEM_INFO:"+itemId+":BASE", JsonUtils.objectToJson(item));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return item;
	}

	@Override
	public EasyUIResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		
		//查询商品信息，紧跟着分析信息的第一个select语句会被分页
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//对结果进行包装
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//构造相应结果
		EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
		
		return result;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		// 1、生成商品id
		long itemId = IDUtils.genItemId();
		// 2、补全TbItem对象的属性
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(item);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		// 7、TaotaoResult.ok()
		
		Destination destination = new ActiveMQTopic("MQ_TOPIC_ITEM_ADD");
		producer.sendMessage(destination, itemId);
		
		return TaotaoResult.ok();

	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		
		//从缓存中查询数据
		try {
			System.out.println("从redis中查询商品描述信息，商品ID："+itemId);
			String redisItemDescInfo = jedisClient.get("ITEM_INFO:"+itemId+":DESC");
			if (!StringUtils.isEmpty(redisItemDescInfo)){
				System.out.println("从redis中查询商品描述信息成功，商品ID："+itemId);
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(redisItemDescInfo, TbItemDesc.class);
				return itemDesc;
			}
			System.out.println("从redis中查询商品描述信息失败，商品ID："+itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		
		//向缓存中添加数据
		try {
			System.out.println("向redis中保存商品描述信息，商品ID："+itemId);
			jedisClient.set("ITEM_INFO:"+itemId+":DESC", JsonUtils.objectToJson(tbItemDesc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tbItemDesc;
	}
	
}
