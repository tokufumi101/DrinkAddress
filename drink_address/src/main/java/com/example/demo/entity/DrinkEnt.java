package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

@Table(name="drink_list")
@Entity
public class DrinkEnt {
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@OneToMany(mappedBy="address_list")
	private long id;

	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy="drinkent")
	private List<AddressEnt> addressEntList;
}
