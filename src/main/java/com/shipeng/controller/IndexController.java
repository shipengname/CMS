package com.shipeng.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
	ThreadPoolTaskExecutor executor;
	
	@Autowired
	private ArticleService service;
	
	@Autowired
	private LinkService linkservice;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	//查询文章
	@SuppressWarnings("unchecked")
	@RequestMapping("select")
	public String select(HttpServletRequest req,Model m,Article article,HttpSession session,
			@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		Article article1 = service.select(article.getId());
		//redis增加点击量
		//获得当前ip
		String ip = req.getRemoteAddr();
		//拼接key
		String key="Hits_"+article1.getId()+ip;
		Boolean hasKey = redisTemplate.hasKey(key);
		//判断是否有值
		if(!hasKey) {
			//调用线程
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					//之前点击量
					Integer hits = article1.getHits();
					//点击量加一
					article1.setHits(hits+1);
					//修改点击量
					service.updateHits(article1);
					System.err.println("点击量已经加一");
					redisTemplate.opsForValue().set(key, "",5, TimeUnit.MINUTES);
				}
			});
		}
		
		//=================kafka增加点击量===================
//		kafkaTemplate.send("articles", "xuefeng="+article1.getId());
		//=================================================
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
