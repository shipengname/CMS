package com.shipeng.service;

import java.util.List;

import com.shipeng.bean.Category;

public interface CategoryService {
	public List<Category> selects(int channelId);
}
