package com.bawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.bean.Slide;
import com.bawei.dao.SlideDao;
import com.bawei.service.SlideService;
@Service
public class SlideServiceImpl implements SlideService {
	@Autowired
	private SlideDao adao;

	@Override
	public List<Slide> selects() {
		return adao.selects();
	}
}
