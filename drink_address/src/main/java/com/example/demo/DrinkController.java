package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		public String add(Model model,AddressEnt addressent,@RequestParam long drink_id) //このままではDBに入れられないので、drink_id部分は変更の良チアr
	{
			model.addAttribute("message","登録完了しました");
			System.out.println(addressent.getAddress());
			System.out.println(drink_id);
			  repository.saveAndFlush(addressent);
			return "top";
	}

}
