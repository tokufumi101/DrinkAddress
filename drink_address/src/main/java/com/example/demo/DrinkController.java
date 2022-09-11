package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.AddressDao;
import com.example.demo.dao.DrinkDao;
import com.example.demo.dto.DrinkDto;
import com.example.demo.entity.AddressEnt;
import com.example.demo.entity.DrinkEnt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DrinkController {

	@Autowired
	DrinkDao drinkRepository;
	@Autowired
	AddressDao addressRepository;

	@GetMapping("/top")
	public String top(Model model) throws Exception {
		model.addAttribute("message", "ようこそ");
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);
		List addressList=addressRepository.findAll();
		model.addAttribute("tableData",addressList);
		RestTemplate restTemplate=new RestTemplate();
		String address="北海道札幌市";
		String makeUrl = "https://msearch.gsi.go.jp/address-search/AddressSearch?q=";
		String sQuote;
		try {
			sQuote = URLEncoder.encode(address, "UTF-8");
			System.out.println(makeUrl+sQuote);
			String a=makeUrl+sQuote;
//			 SuperRequestGeocorder b= restTemplate.getForObject(a,SuperRequestGeocorder.class);
//			 System.out.println(b);
			 String result = "";
			 JsonNode root = null;
		    URL url = new URL(a);
		    HttpURLConnection con = (HttpURLConnection)url.openConnection();
		    con.connect(); // URL接続
		    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); 
		    String tmp = "";

		       while ((tmp = in.readLine()) != null) {
		        result += tmp;
		    }

		    ObjectMapper mapper = new ObjectMapper();
		    root = mapper.readTree(result);
		    in.close();
		    con.disconnect();
//			String b = restTemplate.getForObject(a,String.class);
//	        ObjectMapper mapper = new ObjectMapper();
//	        JsonNode node = mapper.readTree(b);
	        System.out.println(root);
//	        Hoge hoge = mapper.readValue(b, Hoge.class);


			
		} catch (Exception e) {

		      // どういう例外が発生しているか出力する
		      System.out.println("例外クラス: " + e.getClass().getName());

		      // 原因となった例外のチェーンを出力する
		      Throwable cause = e;
		      while ((cause = cause.getCause()) != null) {
		        System.out.println("原因例外クラス: " + cause.getClass().getName());
		      }
		      throw e;
	    }
		
		
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
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);
		
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