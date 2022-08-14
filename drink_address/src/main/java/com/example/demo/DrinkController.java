package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.AddressEnt;



@Controller
public class DrinkController {

	@Autowired
	DrinkDao repository; 
	
	
	@GetMapping("/top")
	public String top(Model model) {
		model.addAttribute("message","ようこそ");
		return "top";
	}
	@PostMapping("/top")
		public String add(Model model,AddressEnt addressent) {
			model.addAttribute("message","登録完了しました");
			  repository.saveAndFlush(addressent);
			return "top";
	}

}
