package com.shipeng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.bean.Comment;
import com.shipeng.dao.ArticleDao;
import com.shipeng.service.ArticleService;
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
		List<Article> list=adao.selectByAdmin(article);
		PageHelper.startPage(pageNum, pageSize);
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

	@Override
	public boolean updateHits(Article article) {
		return adao.updateHits(article)>0;
	}
}
