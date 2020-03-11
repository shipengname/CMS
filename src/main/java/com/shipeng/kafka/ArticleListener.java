package com.shipeng.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shipeng.bean.Article;
import com.shipeng.dao.ArticleDao;
import com.shipeng.dao.ArticleRepository;

public class ArticleListener implements MessageListener<String, String>{
	
	@Autowired
	ArticleDao adao;
	@Autowired
	ArticleRepository articleRepository;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		//接受读取文章保存到mysql
		System.err.println("收到消息");
		String jsonString = data.value();
		if(jsonString.startsWith("shenhe")) {
			//分割字符
			String[] sp = jsonString.split("=");
			Integer id = Integer.parseInt(sp[1]);
			//通过id查询文章
			Article article = adao.select(id);
			//保存到es索引库
			articleRepository.save(article);
			System.err.println("同步es索引库成功");
		}else {
			Article article = JSON.parseObject(jsonString,Article.class);
			adao.add(article);
			System.err.println("保存了文章对象.....");
		}
	}

}
