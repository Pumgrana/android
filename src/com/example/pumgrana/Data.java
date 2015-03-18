package com.example.pumgrana;

// TODO: Auto-generated Javadoc
/**
 * Data object.
 */
public class Data {
	
	/** The _id. */
	private Integer _id;
	
	/** The data id. */
	private String dataId;
	
	/** The title. */
	private String title;
	
	/** The text. */
	private String text;
	
	/**
	 * Instantiates a new data.
	 */
	public Data(){}
	
	/**
	 * Instantiates a new data.
	 *
	 * @param id the id
	 * @param title the title
	 * @param text the text
	 */
	public Data(String id, String title, String text){
		this.dataId = id;
		this.title = title;
		this.text = text;
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
	 * Gets the data id.
	 *
	 * @return the data id
	 */
	public String getDataId(){
		return dataId;
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText(){
		return text;
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
	 * Sets the data id.
	 *
	 * @param dataId the new data id
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text){
		this.text = text;
	}
	
}