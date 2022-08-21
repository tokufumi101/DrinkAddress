package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="drink_list")
@Entity
public class DrinkEnt {
	
//
//	public DrinkEnt(int i, String string) {
//		this.setId(i);
//		this.setName(string);
//	}

	public DrinkEnt(String name) {
		this.name = name;
	}

	@Column(name="id")
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@OneToMany(mappedBy="address_list")
	private long id;

	@Column(name="name")
	private String name;
	
//	@OneToMany(mappedBy="drinkent")
//	private List<AddressEnt> addressEntList;
}
