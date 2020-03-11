package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shipeng.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Favorite;
import com.shipeng.bean.User;
import com.shipeng.dao.FavoriteDao;
import com.shipeng.exception.CMSRuntimeException;
import com.shipeng.service.FavoriteService;
@Service
public class FavoriteServiceImpl implements FavoriteService {
	@Autowired
	private FavoriteDao adao;
	@Override
	public int addFavo(Favorite favorite) throws CMSRuntimeException {
		int i;
		if(!StringUtil.isHttpUrl(favorite.getUrl())) {
			throw new CMSRuntimeException("url不合法");
		}else {
			i=adao.addFavo(favorite);
		}
		return i;
	}
	@Override
	public PageInfo selects(int pageNum, int pageSize,User user) {
		PageHelper.startPage(pageNum, pageSize);
		List<Favorite> list=adao.getFavoList(user);
		PageInfo<Favorite> info = new PageInfo<Favorite>(list);
		return info;
	}
	@Override
	public int delete(String id) {
		return adao.delete(id);
	}
}
