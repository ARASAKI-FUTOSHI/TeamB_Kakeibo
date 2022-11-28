package com.example.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Amount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//IDを生成
	@Column(name="id")
	private Integer amount_id;
	private Integer user_id;
	private Integer date_id;
	private Integer category_id;
	private Integer price;
	private Integer payment_id;
	private String note;
	private String image_path;
	private Integer income_id;
	public Integer getAmount_id() {
		return amount_id;
	}
	public void setAmount_id(Integer amount_id) {
		this.amount_id = amount_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getDate_id() {
		return date_id;
	}
	public void setDate_id(Integer date_id) {
		this.date_id = date_id;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public Integer getIncome_id() {
		return income_id;
	}
	public void setIncome_id(Integer income_id) {
		this.income_id = income_id;
	}
	


}
