package com.bawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.bean.Article;
import com.bawei.bean.Category;
import com.bawei.bean.Channel;
import com.bawei.bean.Comment;
import com.bawei.dao.ArticleDao;
import com.bawei.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleDao adao;

	@Override
	public boolean update(Article article) {
		return adao.update(article)>0;
	}

	@Override
	public PageInfo<Article> selectByAdmin(Article article, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> list=adao.selectByAdmin(article);
		return new PageInfo<Article>(list);
	}

	@Override
	public Article select(Integer id) {
		return adao.select(id);
	}

	@Override
	public List<Channel> selectsChannel() {
		return adao.selectsChannel();
	}

	@Override
	public List<Category> selectsCategory(int id) {
		return adao.selectsCategory(id);
	}

	@Override
	public void add(Article artice) {
		adao.add(artice);
		
	}

	@Override
	public PageInfo<Comment> selectComment(Integer id, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Comment> comments=adao.selectComment(id);
		return new PageInfo<Comment>(comments);
	}

	@Override
	public List<Article> selectArticesContected(Article article1) {
		return adao.selectArticesContected(article1);
	}
}
