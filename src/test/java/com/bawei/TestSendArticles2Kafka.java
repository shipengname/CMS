package com.bawei;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.shipeng.bean.Article;
import com.shipeng.utils.FileUtilIO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:producer.xml")
public class TestSendArticles2Kafka {
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	@Test
	public void testSendArticles2Kafka() throws IOException {
		File file = new File("D:\\文本");
		File[] files = file.listFiles();
		for (File file2 : files) {
			String fileName = file2.getName();
			String title = fileName.replace(".txt", "");
			//System.out.println(title);
			String content = FileUtilIO.readFile(file2, "utf8");
			//System.out.println(content);
			Article article = new Article();
			article.setTitle(title);
			article.setContent(content);
			article.setCategory_id(1);
			article.setChannel_id(2);
			String jsonString = JSON.toJSONString(article);
			kafkaTemplate.send("articles",jsonString);
		}
	}
}
