package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.DrinkEnt;

@Repository
public interface DrinkDao extends JpaRepository<DrinkEnt,String>{
	
}
