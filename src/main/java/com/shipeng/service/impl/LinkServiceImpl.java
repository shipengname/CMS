package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.bean.Link;
import com.shipeng.dao.LinkDao;
import com.shipeng.service.LinkService;
@Service
public class LinkServiceImpl implements LinkService {
	@Autowired
	private LinkDao adao;

	@Override
	public List<Link> selectsLink() {
		return adao.selectsLink();
	}
}
