package com.shipeng.service;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Favorite;
import com.shipeng.bean.User;
import com.shipeng.exception.CMSRuntimeException;

public interface FavoriteService {

	int addFavo(Favorite favorite) throws CMSRuntimeException;

	PageInfo selects(int pageNum, int pageSize, User user);

	int delete(String id);

}
