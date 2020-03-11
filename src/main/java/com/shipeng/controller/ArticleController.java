package com.shipeng.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.dao.ArticleRepository;
import com.shipeng.service.ArticleService;

@RequestMapping("article")
@Controller
public class ArticleController {
	@Autowired
	private ArticleService service;
	
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@RequestMapping("selectsByAdmin")
	public String getArticleList(Model m,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize,Article article) {
		PageInfo<Article> info = service.selectByAdmin(article,pageNum,pageSize);
		m.addAttribute("list", info.getList());
		m.addAttribute("article", article);
		m.addAttribute("info", info);
		m.addAttribute("article", article);
		return "/admin/article";
	}
	/**
	 * 
	    * @Title: 审核文章
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param article
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	@ResponseBody
	@RequestMapping("updateArticle")
	public boolean update(Article article) {
		kafkaTemplate.send("articles","shenhe"+"="+article.getId()+"");
		redisTemplate.delete("hot_articles");
		//通过id查询文章
		//Article article2 = service.select(article.getId());
		//保存到es索引
		//articleRepository.save(article2);
		//System.err.println("同步es索引成功");
		return service.update(article);
	}
	
	@ResponseBody
	@RequestMapping("select")
	public Object select(Integer id) {
		Article article=service.select(id);
		return article;
	}
	
	@RequestMapping("toAdd")
	public Object toAdd() {
		return "my/publish";
	}
	
	@ResponseBody
	@RequestMapping("selectsChannel")
	public Object selectsChannel() {
		List<Channel> list=service.selectsChannel();
		return list;
	}
	
	//selectsCategory
	@ResponseBody
	@RequestMapping("selectsCategory")
	public Object selectsCategory(int id) {
		List<Category> list=service.selectsCategory(id);
		return list;
	}
	@ResponseBody
	@RequestMapping("add")
	public boolean add(Article artice,@RequestParam("file")MultipartFile file) {
		try {
			if(file.getSize()>0) {
				String path="d:/pic/";
				String filename = file.getOriginalFilename();
				String hz = filename.substring(filename.lastIndexOf("."));
				String newfilename = UUID.randomUUID().toString()+hz;
				File file2 = new File(path+newfilename);
				file.transferTo(file2);
				artice.setPicture(newfilename);
			}
			service.add(artice);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	//个人查看文章
	@RequestMapping("selectArticle")
	public String selectArticle(Model m,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize,Article article) {
		PageInfo<Article> info = service.selectByAdmin(article,pageNum,pageSize);
		m.addAttribute("list", info.getList());
		m.addAttribute("article", article);
		m.addAttribute("info", info);
		m.addAttribute("article", article);
		return "/my/article";
	}
}
