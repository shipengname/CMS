package com.bawei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bawei.bean.Category;

public interface CategoryDao {
	//查询本栏目所有的分类
	public List<Category> selects(@Param("channelId")int channelId);
}
