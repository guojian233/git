package com.iqn.bean;
//统计供应商的商品供给数量
public class BillCAS {
	private Long value;//值
	private String name;//商品名
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
