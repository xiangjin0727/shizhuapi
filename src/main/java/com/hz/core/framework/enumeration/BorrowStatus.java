package com.hz.core.framework.enumeration;

public enum BorrowStatus {
	sta1("待初审",1),
	sta2("初审成功",2),
	sta3("初审失败",3),
	sta4("招标中",4),
	sta5("待复审",5),
	sta6("复审失败",6),
	sta7("还款中",7),
	sta8("已还清",8),
	sta9("已流标",9),
	sta10("逾期",10);
	
	private String name;
	private int index;
	
	private BorrowStatus(String name,int index) {
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
