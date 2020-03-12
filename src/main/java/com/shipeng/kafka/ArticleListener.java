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
		}else if(jsonString.startsWith("xuefeng")) {
			//做kafka增加点击量
			String[] sp = jsonString.split("=");
			Integer id = Integer.parseInt(sp[1]);
			Article select = adao.select(id);
			//拿到当前点击量
			Integer hits = select.getHits();
			//点击量加1
			select.setHits(hits+1);
			//更新到数据库
			adao.updateHits(select);
			System.err.println("用kafka增加点击量成功！！！");
		}else {
			Article article = JSON.parseObject(jsonString,Article.class);
			adao.add(article);
			System.err.println("保存了文章对象.....");
		}
	}

}
