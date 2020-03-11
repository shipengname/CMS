package com.shipeng.bean.enums;

public enum Lx {
	HTML("文本"),
	IMAGE("图片");
	private String lxName;
	public String getLxName() {
		return lxName;
	}
	public void setLxName(String lxName) {
		this.lxName = lxName;
	}
	private Lx(String lxName) {
		this.lxName = lxName;
	}
	public String getName() {
		return this.name();
	}
	public int getOrdinal() {
		return this.ordinal();
	}
}
