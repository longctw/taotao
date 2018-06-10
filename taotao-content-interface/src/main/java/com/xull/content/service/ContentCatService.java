package com.xull.content.service;

import java.util.List;

import com.xull.common.pojo.EasyUITreeNode;
import com.xull.common.pojo.TaotaoResult;

public interface ContentCatService {
	
	/**
	 * 查询所有子节点
	 * @param parentId
	 * @return
	 */
	public List<EasyUITreeNode> getContentCatList(long parentId);

	/**
	 * 根据指定的付家店，添加一个子节点
	 * @param name
	 * @param parentId
	 * @return
	 */
	public TaotaoResult createContentCat(String name, long parentId);
	
	/**
	 * 删除一个节点
	 * @param id
	 * @param parentId
	 * @return
	 */
	public TaotaoResult deleteContentCat(long id);
	
	/**
	 * 修改一个节点的name
	 * @param id
	 * @param name
	 * @return
	 */
	public TaotaoResult editContentCat(long id, String name);
}
