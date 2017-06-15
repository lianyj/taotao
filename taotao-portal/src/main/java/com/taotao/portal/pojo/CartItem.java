package com.taotao.portal.pojo;

import java.io.Serializable;

public class CartItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1070639286706907167L;
	private long id;
	private String title;
	private Integer num;
	private long price;
	private String image;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
