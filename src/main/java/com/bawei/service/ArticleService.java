package com.bawei.service;


import java.util.List;

import com.bawei.bean.Article;
import com.bawei.bean.Category;
import com.bawei.bean.Channel;
import com.bawei.bean.Comment;
import com.github.pagehelper.PageInfo;

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
