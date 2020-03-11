package com.shipeng.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.shipeng.bean.Article;

public interface ArticleRepository extends ElasticsearchRepository<Article, Integer>{

	List<Article> findByTitle(String key);

}
