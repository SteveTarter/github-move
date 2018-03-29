package com.tarterware.adsbexchangereader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {

	private Integer id;
	private String name;
	
	public Feed() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Feed {" +
				"id=" + id + 
				", name=" + name + 
				"}";
	}
	
}
