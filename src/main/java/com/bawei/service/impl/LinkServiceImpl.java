package com.bawei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.dao.LinkDao;
import com.bawei.service.LinkService;
@Service
public class LinkServiceImpl implements LinkService {
	@Autowired
	private LinkDao adao;
}
