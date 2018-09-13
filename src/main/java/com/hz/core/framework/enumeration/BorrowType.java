package com.hz.core.framework.enumeration;

public enum BorrowType {
	sta1("信用标",1),
	sta2("抵押标",2);
	
	private String name;
	private int index;
	
	private BorrowType(String name,int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}
}
