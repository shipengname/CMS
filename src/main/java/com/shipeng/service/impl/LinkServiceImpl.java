package com.shipeng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.dao.LinkDao;
import com.shipeng.service.LinkService;
@Service
public class LinkServiceImpl implements LinkService {
	@Autowired
	private LinkDao adao;
}
