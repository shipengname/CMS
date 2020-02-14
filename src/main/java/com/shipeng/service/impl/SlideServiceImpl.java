package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.bean.Slide;
import com.shipeng.dao.SlideDao;
import com.shipeng.service.SlideService;
@Service
public class SlideServiceImpl implements SlideService {
	@Autowired
	private SlideDao adao;

	@Override
	public List<Slide> selects() {
		return adao.selects();
	}
}
