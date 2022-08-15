package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DrinkAddressApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrinkAddressApplication.class, args);
	}

//	@Autowired
//	private DrinkDao dDao;
//	
//	@Override
//	public void run(String... args)throws Exception{
//		DrinkEnt pepsi = new DrinkEnt(1,"ペプシ");
//		dDao.save(pepsi);
//	}
	
}
