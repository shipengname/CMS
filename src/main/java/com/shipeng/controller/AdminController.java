package com.shipeng.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shipeng.utils.StringUtil;

import scala.annotation.serializable;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.bean.Slide;
import com.shipeng.bean.User;
import com.shipeng.dao.ArticleRepository;
import com.shipeng.service.ArticleService;
import com.shipeng.service.CategoryService;
import com.shipeng.service.ChannelService;
import com.shipeng.service.SlideService;
import com.shipeng.service.UserService;
import com.shipeng.utils.CMSJsonUtil;
import com.shipeng.utils.HLUtils;
@Controller
public class AdminController {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SlideService slideService;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	//搜索方法
	@RequestMapping("/search")
	public String search(String key,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		//普通搜索
		//List<Article> list=articleRepository.findByTitle(key);
		//高量搜索
		PageInfo<Article> info = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, pageNum, pageSize, new String[] {"title"}, "id", key);
		model.addAttribute("info", info);
		model.addAttribute("key", key);
		return "index/index";
	}
	//首页的入口
	@SuppressWarnings("unchecked")
	@RequestMapping("index")
	public String index(Model m,Article article,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		//查询所有的栏目
		List<Channel> channelList= channelService.selects();
		m.addAttribute("channelList", channelList);
		//先从redis中查询有没有项目
		List<Channel> redisChannel = redisTemplate.opsForList().range("cms_channel", 0, -1);
		//如果没有先从mysql中查询频道
		if(redisChannel==null||redisChannel.size()==0) {
			System.err.println("从mysql中查询了频道！！");
			List<Channel> channelall = channelService.selects();
			//查询频道后放入redis中
			redisTemplate.opsForList().leftPushAll("cms_channel", channelall.toArray());
			m.addAttribute("channelList", channelall);
		}else {
			//如果有直接放入model
			System.err.println("从redis中查询了频道");
			m.addAttribute("channelList", redisChannel);
		}
		if(article.getChannel_id()!=null) {
			List<Category> cates = categoryService.selects(article.getChannel_id());
			m.addAttribute("cates", cates);
			PageInfo<Article> info = articleService.selectByAdmin(article, pageNum, pageSize);
			m.addAttribute("info", info);
		}else {
			article.setHot(1);
			//查询所有的广告作为轮播图
			List<Slide> slideList=slideService.selects();
			m.addAttribute("slideList", slideList);	
			//查询所有的热门文章
			//先从redis中查查有没有热门文章
			//五分钟实时更新
			//redisTemplate.expire("hot_articles", 5, TimeUnit.MINUTES);
			List<Article> list = redisTemplate.opsForList().range("hot_articles", 0, 5);
			if(list!=null&&list.size()!=0) {
				//redis中有数据
				m.addAttribute("articleList", list);
				System.err.println("redis中的热门文章");
			}else {
				//从mysql中查找
				PageInfo<Article> info = articleService.selectByAdmin(article, pageNum, pageSize);
				redisTemplate.opsForList().leftPushAll("hot_articles", info.getList().toArray());
				m.addAttribute("articleList", info.getList());
				System.err.println("热门文章保存到redis数据库");
			}
		}
		m.addAttribute("article", article);
		article.setHot(0);
		//查询最新文章，显示到首页
		//第一次访问redis中查询数据
		List<Article> list = redisTemplate.opsForList().range("new_articles", 0, 2);
		//看redis中数据是否为空
		if(list!=null||list.size()!=0) {
			System.err.println("从redis中查询了最新文章");
			m.addAttribute("newArcitles", list);
		}else {
			//如果redis数据库中没有数据从MySQL中查询数据
			System.err.println("从MySQL中查询最新文章");
			PageInfo<Article> info2 = articleService.selectByAdmin(article, pageNum, pageSize);
			System.err.println("最新文章保存到redis数据库中");
			//最新文章保存到redis数据库中
		    redisTemplate.opsForList().leftPushAll("new_articles", info2.getList().toArray());
		    m.addAttribute("newArcitles", info2.getList());
		}
		return "index/index";
	}
	
	//后台管理的人口
	@RequestMapping("admin")
	public String admin() {
		return "admin/index";
	}
	
	//个人中心的入口
	@RequestMapping("my")
	public String admin(Model m,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize,Article article) {
		PageInfo<Article> info = articleService.selectByAdmin(article,pageNum,pageSize);
		m.addAttribute("list", info.getList());
		m.addAttribute("article", new Article());
		m.addAttribute("info", info);
		m.addAttribute("article", article);
		return "my/index";
	}
	
	@RequestMapping("admin/login")
	public String toLogin() {
		return "admin/login";
	}

	
	//登錄
	@ResponseBody
	@RequestMapping("login")
	public Object login(User user,HttpSession session) {
		CMSJsonUtil cju = new CMSJsonUtil();
		boolean uname = StringUtil.isNotEmpty(user.getUsername());
		boolean upwd = StringUtil.isNotEmpty(user.getPassword());
		if(!uname||!upwd) {
			cju.setMsg("用戶名密碼不能爲空");
			return cju;
		}
		User u=userService.login(user);
		if(u==null) {
			cju.setMsg("用戶名不存在");
			return cju;
		}
		//验证是否是管理员
		if(!u.getRole().equals("1")) {
			cju.setMsg("请输入管理员帐户");
			return cju;
		}
		if(u.getLocked()==1) {
			cju.setMsg("該用戶被禁用請聯繫管理員");
			return cju;
		}
		if(!user.getPassword().equals(u.getPassword())) {
			cju.setMsg("密碼錯誤");
			return cju;
		}
		session.setAttribute("user", u);
		cju.setMsg("true");
		return cju;
	}
}
