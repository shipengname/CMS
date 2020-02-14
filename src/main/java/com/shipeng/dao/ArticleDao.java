package com.shipeng.dao;


import java.util.List;

import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.bean.Comment;

public interface ArticleDao {

	public int update(Article article);

	public List<Article> selectByAdmin(Article article);

	public Article select(Integer id);

	public List<Channel> selectsChannel();

	public List<Category> selectsCategory(Integer id);

	public void add(Article artice);

	public List<Comment> selectComment(Integer id);

	public List<Article> selectArticesContected(Article article1);
}
