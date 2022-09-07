package com.example.demo.connection;

import java.util.List;

import lombok.Data;

@Data
public class RequestGeocoder {
	private List<Geometry> geometry;
	private String type;
	private List<Properties> properties;
}
