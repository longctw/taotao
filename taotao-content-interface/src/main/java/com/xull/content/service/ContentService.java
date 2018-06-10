package com.xull.content.service;

import java.util.List;

import com.xull.common.pojo.EasyUIResult;
import com.xull.common.pojo.TaotaoResult;
import com.xull.pojo.TbContent;

public interface ContentService {
	
	/**
	 * 根据类别查询所有的内容信息，分页处理
	 * @param categoryId
	 * @param page
	 * @param row
	 * @return
	 */
	public EasyUIResult contentListByCat(long categoryId, int page, int row);
	/**
	 * 保存一条内容信息
	 * @param content
	 * @return
	 */
	public TaotaoResult saveContent(TbContent content);
	
	/**
	 * 根据分类查询内容信息
	 * @param cid
	 * @return
	 */
	public List<TbContent> contentListByCid(long cid);
	
}
