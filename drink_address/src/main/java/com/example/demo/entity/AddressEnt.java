package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name="address_list")
public class AddressEnt{
	
//	@Column(name="id")
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
//	@Column(name="encount_date")
	private Timestamp encountDate;
	
	@ManyToOne()
	@JoinColumn(name="drink_id",referencedColumnName = "id")
	private DrinkEnt drinkId;
	
//	@Column(name="address")
	private String address;
}
