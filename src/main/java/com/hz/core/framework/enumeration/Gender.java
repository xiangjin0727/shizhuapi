package com.hz.core.framework.enumeration;

public enum Gender
{
	/*
	 * 女性
	 */
	FEMALE("女性",1),
	/*
	 * 男性
	 */
	MALE("男性",2);
	
	private String name;
	private int index;
	
	private Gender(String name, int index)
	{
		this.name = name;
		this.index = index;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int index)
	{
		this.index = index;
	}
	
	public static Gender getGender(int index)
	{
		Gender g = null;
		switch (index)
		{
			case 1:
				g = Gender.FEMALE;
				break;
			default:
				g = Gender.MALE;
				break;
		}
		return g;
		
	}
	
}
