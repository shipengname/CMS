package com.shipeng.dao;

import java.util.List;

import com.shipeng.bean.Favorite;
import com.shipeng.bean.User;

public interface FavoriteDao {

	int addFavo(Favorite favorite);

	List<Favorite> getFavoList(User user);

	int delete(String id);

}
