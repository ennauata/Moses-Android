package com.moses.Bin;

import java.util.ArrayList;

public class GroupBin {
	private String name;
	private Integer id;
	private String image;
	private Integer creator;
	private String status;
	private ArrayList<String> members; 
	
	public GroupBin(String name, Integer id, String image, Integer creator,
			String status) {
		super();
		this.name = name;
		this.id = id;
		this.image = image;
		this.creator = creator;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<String> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<String> members) {
		this.members = members;
	}
	
	
}
