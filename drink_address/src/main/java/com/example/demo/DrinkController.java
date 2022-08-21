package com.example.demo;

import java.util.List;

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
	    List list =drinkRepository.findAll();
	    model.addAttribute("data", list); 
		return "top";
	}

	
	@PostMapping("/top")
		public String add(@RequestParam("name")String name, Model model,
				DrinkEnt drinkEnt,AddressEnt addressEnt,
				@ModelAttribute DrinkDto drinkDto){
			
		DrinkEnt drinkdata = drinkRepository.findByName(name);
		List list = drinkRepository.findAll();
		if( drinkdata==null) {
			System.out.println("DBにないよ");	
			//新しいドリンクの保存
			DrinkEnt newdrink = new DrinkEnt (name);
			newdrink.setId(list.size()+1); //今後、変更の必要あり（generatedvalueとかでもっといい感じにできそうなので）
			drinkRepository.save(newdrink);
			addressEnt.setDrinkId(newdrink.getId());
		}else {
			System.out.println("DBにあるよ");
			addressEnt.setDrinkId(drinkdata.getId());
		}
		
		//addressentの保存
		addressEnt.setAddress(drinkDto.getAddress());
		addressRepository.saveAndFlush(addressEnt);

			model.addAttribute("message","登録完了しました");
			
			return "top";
	}

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
