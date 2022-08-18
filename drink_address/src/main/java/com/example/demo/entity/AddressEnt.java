package com.example.demo.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="address_list")
public class AddressEnt{
	
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="encount_date")
	private Timestamp encountDate;
	

//	@ManyToOne
//	@JoinColumn(name="drink_list_id",referencedColumnName = "id")
	private long drinkId;

	
	@Column(name="address")
	private String address;
}
