package com.example.pumgrana;

// TODO: Auto-generated Javadoc
/**
 * Tag object.
 */
public class Tag {
	
	/** The _id. */
	private Integer _id;
	
	/** The tag id. */
	private String tagId;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new tag.
	 */
	public Tag() {}
	
	/**
	 * Instantiates a new tag.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public Tag(String id, String name) {
		this.tagId = id;
		this.name = name;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId(){
		return _id;
	}
	
	/**
	 * Gets the tag id.
	 *
	 * @return the tag id
	 */
	public String getTagId(){
		return tagId;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id){
		this._id = id;
	}
	
	/**
	 * Sets the tag id.
	 *
	 * @param tagId the new tag id
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
