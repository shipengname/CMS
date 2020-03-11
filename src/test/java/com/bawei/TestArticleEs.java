package com.bawei;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shipeng.bean.Article;
import com.shipeng.dao.ArticleDao;
import com.shipeng.dao.ArticleRepository;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class TestArticleEs {
	@Autowired
	private ArticleDao adao;
	@Autowired
	private ArticleRepository articleRepository;
	@Test
	public void testImport() {
		List<Article> list = adao.selectByAdmin(null);
		articleRepository.saveAll(list);
		System.out.println("导入成功");
	}
}
