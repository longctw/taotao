package com.xull.manager.service;

import java.util.List;

import com.xull.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	public List<EasyUITreeNode> getItemCatByParentId(long parentId);
	
}
