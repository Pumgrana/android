package com.example.pumgrana;

public class DataTag {
	private Integer _id;
	private String dataId;
	private String tagId;
	
	DataTag() {}
	
	DataTag(String dId, String tId) {
		this.dataId = dId;
		this.tagId = tId;
	}
	
	public String getDataId() {
		return this.dataId;
	}
	
	public String getTagId() {
		return this.tagId;
	}
	
	public void setDataId(String id) {
		this.dataId = id;
	}
	
	public void setTagId(String id) {
		this.tagId = id;
	}
}
