package com.xull.content.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xull.common.pojo.EasyUITreeNode;
import com.xull.common.pojo.TaotaoResult;
import com.xull.content.service.ContentCatService;
import com.xull.mapper.TbContentCategoryMapper;
import com.xull.pojo.TbContentCategory;
import com.xull.pojo.TbContentCategoryExample;
import com.xull.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCatServiceImpl implements ContentCatService {
	
	@Autowired
	private TbContentCategoryMapper contentCatMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCatMapper.selectByExample(example);
		
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for(TbContentCategory cat : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setText(cat.getName());
			node.setState(cat.getIsParent() ? "closed":"open");
			result.add(node);
		}
		
		return result;
	}

	@Override
	public TaotaoResult createContentCat(String name, long parentId) {
		TbContentCategory cat = new TbContentCategory();
		Date currentDate = new Date();
		//补充数据
		cat.setName(name);
		cat.setParentId(parentId);
		cat.setIsParent(false);
		cat.setCreated(currentDate);
		cat.setUpdated(currentDate);
		//状态。可选值:1(正常),2(删除)
		cat.setStatus(1);
		//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
		cat.setSortOrder(1);
		// c)向tb_content_category表中插入数据
		contentCatMapper.insert(cat);
		// 3、判断父节点的isparent是否为true，不是true需要改为true。
		TbContentCategory parentNode = contentCatMapper.selectByPrimaryKey(parentId);
		if (!parentNode.getIsParent()) {
			parentNode.setIsParent(true);
			//更新父节点
			contentCatMapper.updateByPrimaryKey(parentNode);
		}

		return TaotaoResult.ok(cat);
	}

	@Override
	public TaotaoResult deleteContentCat(long id) {
		TbContentCategory category = contentCatMapper.selectByPrimaryKey(id);
		deleteChildNode(category);
		
		//查询出父节点的所有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(category.getParentId());
		List<TbContentCategory> childNodeList = contentCatMapper.selectByExample(example);
		
		//3、判断父节点还有没有子节点，如果没有，将父节点设为叶子节点
		if (childNodeList == null || childNodeList.size() == 0){
			TbContentCategory parentNode = contentCatMapper.selectByPrimaryKey(category.getParentId());
			parentNode.setIsParent(false);
			//更新父节点
			contentCatMapper.updateByPrimaryKey(parentNode);
		}
		
		return TaotaoResult.ok();
	}

	private void deleteChildNode(TbContentCategory category) {
		
		//删除当前节点，及其所有子节点
		contentCatMapper.deleteByPrimaryKey(category.getId());
		
		if (category.getIsParent()){
			//查询出所有的子节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(category.getId());
			List<TbContentCategory> childNodeList = contentCatMapper.selectByExample(example);
			
			for(TbContentCategory cat : childNodeList){
				deleteChildNode(cat);
			}
		}
	}

	@Override
	public TaotaoResult editContentCat(long id, String name) {
		TbContentCategory cat = contentCatMapper.selectByPrimaryKey(id);
		cat.setName(name);
		contentCatMapper.updateByPrimaryKey(cat);
		return TaotaoResult.ok(cat);
	}

}
