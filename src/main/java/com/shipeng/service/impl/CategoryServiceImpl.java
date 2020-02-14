package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.bean.Category;
import com.shipeng.dao.CategoryDao;
import com.shipeng.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao adao;
	
	@Override
	public List<Category> selects(int channelId) {
		return adao.selects(channelId);
	}

}
