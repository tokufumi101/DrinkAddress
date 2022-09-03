package com.example.demo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("message", "ようこそ");
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);
		List addressList=addressRepository.findAll();
		model.addAttribute("tableData",addressList);
		
		
		
		return "top";
	}

	@PostMapping("/top")
	public String add(@RequestParam("name") String name, Model model, DrinkEnt drinkEnt, AddressEnt addressEnt,
			@ModelAttribute DrinkDto drinkDto,RedirectAttributes redirectAttributes) {

		// 飲み物リストをプルダウンで表示
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);
		model.addAttribute("message", "登録完了しました");
		
		// ドリンクリストに新しいドリンクの登録
		if (drinkRepository.existsByName(name) == false) {
			DrinkEnt drinkPlus = new DrinkEnt(name);
			System.out.println(name);
			
			drinkPlus.setId(list.size()+1);
			drinkRepository.saveAndFlush(drinkPlus);
		}

		addressEnt.setAddress(drinkDto.getAddress());

		// timestampを入れる
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		addressEnt.setRegisterDate(timestamp);

		DrinkEnt drink = drinkRepository.findByName(name);
		
		addressEnt.setDrinkEnt(drink);
		System.out.println(addressEnt.getDrinkEnt().getName());
		
		// データがそろったのでテーブルに登録
		addressRepository.saveAndFlush(addressEnt);
		
		List addressList=addressRepository.findAll();

		model.addAttribute("tableData",addressList);
		redirectAttributes.addFlashAttribute("flashmsg","登録完了しました");
		return "redirect:/top";
	}
	
	@PostMapping("/select")
//	public String select(Model model,@RequestParam("select") DrinkEnt select) {
	public String select(Model model,@RequestParam("id,name") String idName) {
		
		String[]splitedIdName=idName.split(",");
		
		
		DrinkEnt drinkEnt=new DrinkEnt();
		long splitedId=Long.parseLong(splitedIdName[0]);
		drinkEnt.setId(splitedId);
		drinkEnt.setName(splitedIdName[1]);
		List selectedList=addressRepository.findByDrinkEnt(drinkEnt);
//		List<AddressEnt> selectedList=addressRepository.findAll();	
		model.addAttribute("selectedList",selectedList);
		
		return "list";
	}
	@GetMapping("/select")
//	public String select(Model model,@RequestParam("select") DrinkEnt select) {
//	public String select2(Model model) {
		public String select2(Model model,
				@RequestParam(name="id,name",value="id,name",required=false) String idName) {
		
		String[]splitedIdName=idName.split(",");
		
		
		DrinkEnt drinkEnt=new DrinkEnt();
		long splitedId=Long.parseLong(splitedIdName[0]);
		drinkEnt.setId(splitedId);
		drinkEnt.setName(splitedIdName[1]);
		List selectedList=addressRepository.findByDrinkEnt(drinkEnt);
		model.addAttribute("selectedList",selectedList);
		
		return "list";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam("Id") Long Id,
			@RequestParam("id,name")String idName,
			RedirectAttributes redirectAttributes){
		addressRepository.deleteById(Id);
		redirectAttributes.addAttribute("id,name",idName);
		return "redirect:/select";
	}

	

}