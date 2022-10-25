package com.root.ddoopgg.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.root.ddoopgg.user.service.UserService;

@Controller
public class UserController {
	@Autowired UserService us;
	
	
	@GetMapping(value = "summoner", produces="text/plain;charset=UTF-8")
	public String summoner(String name,Model model) {		
		us.getSummoner(name.replace(" ", ""),model);
		return "Summoner/summoner";
	}
}
