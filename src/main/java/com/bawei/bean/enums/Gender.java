package com.bawei.bean.enums;

public enum Gender {
	MAN("男"),
	WOMAN("女");
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}
	//有参构造
	private Gender(String displayName) {
		this.displayName = displayName;
	}
	//提高一个获得常量的方法
	public String getName() {
		return this.name();
	}
	//提供一个获得下标的方法
	public int getOrdinal() {
		return this.ordinal();
	}
}
