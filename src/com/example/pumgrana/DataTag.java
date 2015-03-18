package com.example.pumgrana;

// TODO: Auto-generated Javadoc
/**
 * DataTag object.
 */
public class DataTag {
	
	/** The _id. */
	private Integer _id;
	
	/** The data id. */
	private String dataId;
	
	/** The tag id. */
	private String tagId;
	
	/**
	 * Instantiates a new data tag.
	 */
	DataTag() {}
	
	/**
	 * Instantiates a new data tag.
	 *
	 * @param dId the d id
	 * @param tId the t id
	 */
	DataTag(String dId, String tId) {
		this.dataId = dId;
		this.tagId = tId;
	}
	
	/**
	 * Gets the data id.
	 *
	 * @return the data id
	 */
	public String getDataId() {
		return this.dataId;
	}
	
	/**
	 * Gets the tag id.
	 *
	 * @return the tag id
	 */
	public String getTagId() {
		return this.tagId;
	}
	
	/**
	 * Sets the data id.
	 *
	 * @param id the new data id
	 */
	public void setDataId(String id) {
		this.dataId = id;
	}
	
	/**
	 * Sets the tag id.
	 *
	 * @param id the new tag id
	 */
	public void setTagId(String id) {
		this.tagId = id;
	}
}
