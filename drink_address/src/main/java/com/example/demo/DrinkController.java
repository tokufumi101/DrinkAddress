package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.AddressDao;
import com.example.demo.dao.DrinkDao;
import com.example.demo.dto.DrinkDto;
import com.example.demo.entity.AddressEnt;
import com.example.demo.entity.DrinkEnt;



@Controller
public class DrinkController {

	@Autowired
	DrinkDao drinkRepository; 
	@Autowired
	AddressDao addressRepository;
	
	
	
	@GetMapping("/top")
	public String top(Model model) {
		model.addAttribute("message","ようこそ");
		return "top";
	}

	
	@PostMapping("/top")
		public String add(@RequestParam("id&name")String idName, Model model,
				DrinkEnt drinkEnt,AddressEnt addressEnt,
				@ModelAttribute DrinkDto drinkDto){
			
			
			String idNamelist []=idName.split("&");
			long id=Long.parseLong(idNamelist[0]);
			String name = idNamelist[1];
			System.out.println(id);
			System.out.println(name);
			drinkEnt.setId(id);  
			drinkEnt.setName(name);
			drinkRepository.saveAndFlush(drinkEnt); 
			model.addAttribute("message","登録完了しました");
//			System.out.println(addressent.getAddress());
//			System.out.println(drink_id);
			
//			System.out.println(addressent.getDrinkent());
			addressEnt.setAddress(drinkDto.getAddress());
			addressEnt.setDrinkId(id);
			addressRepository.saveAndFlush(addressEnt);
//			  
//			repository.saveAndFlush(drinkEnt);  
//			System.out.println(drinkEnt.getId());
//			System.out.println(drinkEnt.getName());
			
			return "top";
	}
//	@PostMapping("/top")
//		public String add(Model model,AddressEnt addressent) {
//			model.addAttribute("message","登録完了しました");
//			  repository.saveAndFlush(addressent);
//			  
//			return "list";
//	}
	@GetMapping("/list")
	public String list(Model model,AddressEnt addressent) {
//		System.out.println(addressent.getId());
//		System.out.println(addressent.getAddress());
//		repository.saveAndFlush(addressent);
////		addressRepository.saveAndFlush();
//		System.out.println(addressent.getId());
//		System.out.println(addressent.getAddress());
		return "list";

	}
	

}
