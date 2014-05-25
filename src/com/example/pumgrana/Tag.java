package com.example.pumgrana;

public class Tag {
	private Integer _id;
	private String tagId;
	private String name;
	
	public Tag() {}
	
	public Tag(String id, String name) {
		this.tagId = id;
		this.name = name;
	}
	
	public Integer getId(){
		return _id;
	}
	
	public String getTagId(){
		return tagId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setId(Integer id){
		this._id = id;
	}
	
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
