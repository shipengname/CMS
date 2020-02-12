package com.bawei.service;

import java.util.List;

import com.bawei.bean.Category;

public interface CategoryService {
	public List<Category> selects(int channelId);
}
