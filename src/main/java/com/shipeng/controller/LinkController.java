package com.shipeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shipeng.bean.Link;
import com.shipeng.service.LinkService;

@Controller
@RequestMapping("link")
public class LinkController {
	@Autowired
	private LinkService service;
	@RequestMapping("add")
	private String add(Integer id,Model model) {
		return"";
	}
	@RequestMapping("selectsLink")
	public String selectsLink(Model m) {
		List<Link> list=service.selectsLink();
		m.addAttribute("list", list);
		return "/my/link";
	}
}
