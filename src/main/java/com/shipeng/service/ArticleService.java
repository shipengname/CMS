package com.shipeng.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.bean.Comment;

public interface ArticleService {
	public boolean update(Article article);

	public PageInfo<Article> selectByAdmin(Article article, Integer pageNum, Integer pageSize);

	public Article select(Integer id);

	public List<Channel> selectsChannel();

	public List<Category> selectsCategory(int id);

	public void add(Article artice);

	public PageInfo<Comment> selectComment(Integer id, Integer pageNum, Integer pageSize);

	public List<Article> selectArticesContected(Article article1);

}
