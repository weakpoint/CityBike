package edu.citybike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
	
	@RequestMapping("/")
	public String showIndexView() {
		return "index";
	}

}
