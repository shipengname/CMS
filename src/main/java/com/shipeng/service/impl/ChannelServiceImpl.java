package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.bean.Channel;
import com.shipeng.dao.ChannelDao;
import com.shipeng.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelDao adao;
	
	public List<Channel> selects(){
		return adao.selects();
	}
}
