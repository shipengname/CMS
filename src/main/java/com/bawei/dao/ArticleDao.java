package com.bawei.dao;


import java.util.List;

import com.bawei.bean.Article;
import com.bawei.bean.Category;
import com.bawei.bean.Channel;
import com.bawei.bean.Comment;

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
