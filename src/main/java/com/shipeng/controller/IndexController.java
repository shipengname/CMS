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
	private ArticleService service;
	
	@Autowired
	private LinkService linkservice;
	
	@Autowired
	ThreadPoolTaskExecutor executor;
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
		//=================kafka增加点击量===================
//		kafkaTemplate.send("articles", "xuefeng="+article1.getId());
		//=================================================
		//===============redis增加点击量==================
		//获取ip地址
		String ip = req.getRemoteAddr();
		//拼接key
		String key="hit_s"+article.getId()+"_"+ip;
		Boolean hasKey = redisTemplate.hasKey(key);
		//如果redis数据库中没有数据
		if(!hasKey) {
			//使用spring线程使点击量+1
			executor.execute(new Runnable() {
		
				@Override
				public void run() {
					//获取原来的点击量
					Integer hits = article1.getHits();
					//点击量加1
					article1.setHits(hits+1);
					//修改点击量加1
					service.updateHits(article1);
					System.err.println("点击量已经加1");
					//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，value为空值的记录，而且有效时长为5分钟
					redisTemplate.opsForValue().set(key, "",5,TimeUnit.MINUTES);
				}
			});
		}
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
