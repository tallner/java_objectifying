package models;

import java.util.Date;

public class Product {
	private Date orderDate;
	private String region;
	private String rep1, rep2;
	private String item;
	private long units;
	private float unitCost;
	private float total;
	
	public Product() {
		this.orderDate = new Date();
		this.region = "";
		this.rep1 = "";
		this.rep2 = "";
		this.item = "";
		this.units = 0;
		this.unitCost = 0.0f;
		this.total = 0.0f;
	}
	
	public Product(Date orderDate, String region, String rep1, String rep2, String item, long units, float unitCost, float total) {
		this.orderDate = orderDate;
		this.region = region;
		this.rep1 = rep1;
		this.rep2 = rep2;
		this.item = item;
		this.units = units;
		this.unitCost = unitCost;
		this.total = total;
	}
	
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRep1() {
		return rep1;
	}
	public void setRep1(String rep1) {
		this.rep1 = rep1;
	}
	public String getRep2() {
		return rep2;
	}
	public void setRep2(String rep2) {
		this.rep2 = rep2;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public long getUnits() {
		return units;
	}
	public void setUnits(long units) {
		this.units = units;
	}
	public float getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Product [orderDate=" + orderDate + ", region=" + region + ", rep1=" + rep1 + ", rep2=" + rep2
				+ ", item=" + item + ", units=" + units + ", unitCost=" + unitCost + ", total=" + total + "]";
	}
	
	
	
	

}
