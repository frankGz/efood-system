package com.gz.efood.model.beans;

/**
 * @author frank.gz
 * this is a bean for category
 */
public class Category
{
	private int id;
	private String name;
	private String description;

	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param picture
	 */
	public Category(int id, String name, String description)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	/**
	 * For c3p0 reflection
	 */
	public Category() {
		
	}
	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	
	
	
}
