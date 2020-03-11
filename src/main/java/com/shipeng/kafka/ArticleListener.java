package com.shipeng.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shipeng.bean.Article;
import com.shipeng.dao.ArticleDao;

public class ArticleListener implements MessageListener<String, String>{
	
	@Autowired
	ArticleDao adao;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		System.err.println("收到消息");
		String jsonString = data.value();
		Article article = JSON.parseObject(jsonString,Article.class);
		adao.add(article);
		System.err.println("保存了文章对象.....");
	}

}
