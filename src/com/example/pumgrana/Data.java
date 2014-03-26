package com.example.pumgrana;

public class Data {
	private String _id;
	private String title;
	private String text;
	
	public Data(){}
	
	public Data(String id, String title, String text){
		this._id = id;
		this.title = title;
		this.text = text;
	}
	
	public String getId(){
		return _id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getText(){
		return text;
	}
	
	public void setId(String id){
		this._id = id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
}