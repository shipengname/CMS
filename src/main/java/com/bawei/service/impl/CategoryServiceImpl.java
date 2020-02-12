package com.bawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.bean.Category;
import com.bawei.dao.CategoryDao;
import com.bawei.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao adao;
	
	@Override
	public List<Category> selects(int channelId) {
		return adao.selects(channelId);
	}

}
