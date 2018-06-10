package com.xull.manager.service;

import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.pojo.TbItem;
import com.xull.pojo.TbItemDesc;

public interface ItemService {
	
	/**
	 * 根据主键查询一条商品信息
	 * @param itemId
	 * @return
	 */
	public TbItem getItemById(long itemId);
	
	/**
	 * 查询所有商品信息，使用PageHelper分页
	 * @param page
	 * @param rows
	 * @return
	 */
	public EasyUIResult getItemList(int page, int rows);
	
	/**
	 * 向数据库中添加一条商品信息
	 * @param tbItem
	 * @return
	 */
	public TaotaoResult saveItem(TbItem tbItem, String desc);
	
	/**
	 * 根据id查询商品详情
	 * @param itemId
	 * @return
	 */
	public TbItemDesc getItemDescById(long itemId);	
}
