package com.bawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.bean.Channel;
import com.bawei.dao.ChannelDao;
import com.bawei.service.ChannelService;
@Service
public class ChannelServiceImpl implements ChannelService{
	@Autowired
	private ChannelDao adao;
	
	public List<Channel> selects(){
		return adao.selects();
	}
}
