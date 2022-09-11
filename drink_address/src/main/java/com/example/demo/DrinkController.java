package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
		List addressList = addressRepository.findAll();
		model.addAttribute("tableData", addressList);
//		RestTemplate restTemplate=new RestTemplate();
//		String address = "北海道札幌市";
//		String makeUrl = "https://msearch.gsi.go.jp/address-search/AddressSearch?q=";
//		String sQuote = null;
//		String result = "";
//		JsonNode root = null;
//		try {
//			sQuote = URLEncoder.encode(address, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String a = makeUrl + sQuote;
//		System.out.println(a);
//		try {
//			URL url = new URL(a);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.connect(); // URL接続
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//			String tmp = "";
//			while ((tmp = in.readLine()) != null) {
//		        result += tmp;
//			}
//			 ObjectMapper mapper = new ObjectMapper();
//			    root = mapper.readTree(result);
//			    in.close();
//			    con.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(root);
//		 for( JsonNode reqgeo : root) {
//	        	System.out.println(reqgeo.get("geometry").get("coordinates")); //緯度経度取得
//	        }
//		double json=root.get("geometry").get("coordinates").doubleValue();
//		System.out.println(json);

//		ObjectMapper objectMapper = new ObjectMapper();
//		SuperRequestGeocoder myData=objectMapper.convertValue(root, SuperRequestGeocoder.class);

//		SuperRequestGeocoder hoge=objectMapper.readValue(root, SuperRequestGeocoder.class);
//		List<RequestGeocoder> rgList = objectMapper.convertValue(root, new TypeReference<List<RequestGeocoder>>() {});
//		System.out.println(rgList.get(0).getGeometry().getCoordinates().get(0).getLatitude());
//		try {
//			sQuote = URLEncoder.encode(address, "UTF-8");
//			System.out.println(makeUrl+sQuote);
//			String a=makeUrl+sQuote;
//			 SuperRequestGeocoder b= restTemplate.getForObject(a,SuperRequestGeocoder.class);
//			 System.out.println(b);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}


		
		return "top";
	}

	@PostMapping("/top")
	public String add(@RequestParam("name") String name, Model model, DrinkEnt drinkEnt, AddressEnt addressEnt,
			@ModelAttribute DrinkDto drinkDto, RedirectAttributes redirectAttributes) {

		// 飲み物リストをプルダウンで表示
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);
		model.addAttribute("message", "登録完了しました");

		// ドリンクリストに新しいドリンクの登録
		if (drinkRepository.existsByName(name) == false) {
			DrinkEnt drinkPlus = new DrinkEnt(name);
			System.out.println(name);

			drinkPlus.setId(list.size() + 1);
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

		String address = addressEnt.getAddress();
		String makeUrl = "https://msearch.gsi.go.jp/address-search/AddressSearch?q=";
		String sQuote = null;
		String result = "";
		JsonNode root = null;
		try {
			sQuote = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String a = makeUrl + sQuote;
		System.out.println(a);
		try {
			URL url = new URL(a);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(root);
//		 for( JsonNode reqgeo : root) {
//	        	System.out.println(reqgeo.get("geometry").get("coordinates")); //緯度経度取得
//	        	
//	        }
//		 addressEnt.setLatitude();

		double lat = root.get(0).get("geometry").get("coordinates").get(0).asDouble();
		double lon = root.get(0).get("geometry").get("coordinates").get(1).asDouble();
		addressEnt.setLatitude(lat);
		addressEnt.setLongitude(lon);
		addressRepository.saveAndFlush(addressEnt);

		List addressList = addressRepository.findAll();

		model.addAttribute("tableData", addressList);
		redirectAttributes.addFlashAttribute("flashmsg", "登録完了しました");

		return "redirect:/top";
	}

	@PostMapping("/select")
//	public String select(Model model,@RequestParam("select") DrinkEnt select) {
	public String select(Model model, @RequestParam("id,name") String idName) {

		String[] splitedIdName = idName.split(",");

		DrinkEnt drinkEnt = new DrinkEnt();
		long splitedId = Long.parseLong(splitedIdName[0]);
		drinkEnt.setId(splitedId);
		drinkEnt.setName(splitedIdName[1]);
		List selectedList = addressRepository.findByDrinkEnt(drinkEnt);
//		List<AddressEnt> selectedList=addressRepository.findAll();	
		model.addAttribute("selectedList", selectedList);
		List list = drinkRepository.findAll();
		model.addAttribute("data", list);

		return "list";
	}

	@GetMapping("/select")
//	public String select(Model model,@RequestParam("select") DrinkEnt select) {
//	public String select2(Model model) {
	public String select2(Model model,
			@RequestParam(name = "id,name", value = "id,name", required = false) String idName) {

		String[] splitedIdName = idName.split(",");

		DrinkEnt drinkEnt = new DrinkEnt();
		long splitedId = Long.parseLong(splitedIdName[0]);
		drinkEnt.setId(splitedId);
		drinkEnt.setName(splitedIdName[1]);
		List selectedList = addressRepository.findByDrinkEnt(drinkEnt);
		model.addAttribute("selectedList", selectedList);

		return "list";
	}

	@PostMapping("/delete")
	public String delete(@RequestParam("Id") Long Id, @RequestParam("id,name") String idName,
			RedirectAttributes redirectAttributes) {
		addressRepository.deleteById(Id);
		redirectAttributes.addAttribute("id,name", idName);
		return "redirect:/select";
	}

}