package com.shipeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shipeng.service.LinkService;

@Controller
@RequestMapping("/my/link/")
public class LinkController {
	@Autowired
	private LinkService service;
	@RequestMapping("add")
	private String add(Integer id,Model model) {
		return"";
	}
	
}
