package com.example.pumgrana;

public class Data {
	private Integer _id;
	private String dataId;
	private String title;
	private String text;
	
	public Data(){}
	
	public Data(String id, String title, String text){
		this.dataId = id;
		this.title = title;
		this.text = text;
	}
	
	public Integer getId(){
		return _id;
	}
	
	public String getDataId(){
		return dataId;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getText(){
		return text;
	}
	
	public void setId(Integer id){
		this._id = id;
	}
	
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
}