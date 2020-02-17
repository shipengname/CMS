package com.shipeng.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Comment;
import com.shipeng.bean.Link;
import com.shipeng.service.ArticleService;
import com.shipeng.service.LinkService;

@Controller
@RequestMapping("indexs")
public class IndexController {
	@Autowired
	private ArticleService service;
	
	@Autowired
	private LinkService linkservice;
	@RequestMapping("select")
	public String select(Model m,Article article,HttpSession session,
			@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		Article article1 = service.select(article.getId());
		System.out.println("=============="+article1);
		PageInfo<Comment> info=service.selectComment(article.getId(),pageNum,pageSize);
		List<Article> articles=service.selectArticesContected(article1);
		List<Link> list=linkservice.selectsLink();
		m.addAttribute("list", list);
		m.addAttribute("article", article1);
		m.addAttribute("comment", info.getList());
		m.addAttribute("info", info);
		m.addAttribute("articles", articles);
		return "index/article";
	}
}
