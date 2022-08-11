package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DrinkController {
	@RequestMapping("/top")
	public String top(Model model) {
		model.addAttribute("message","ようこそ");
		return "top";
	}
}
