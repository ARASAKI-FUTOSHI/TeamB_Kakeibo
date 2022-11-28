package com.example.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AmountView {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//IDを生成
	private Integer id;
	private Integer user_id;
	private Integer category_id;
	private String name;
	private Integer income_id;
	private String income_name;
	private String payment_methods;
	private Integer payment_id;
	private Date date;
	private Integer date_id;
	private Integer price;
	private String note;
	private String image_path;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getDate_id() {
		return date_id;
	}
	public void setDate_id(Integer date_id) {
		this.date_id = date_id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
	public Integer getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}
	public Integer getIncome_id() {
		return income_id;
	}
	public void setIncome_id(Integer income_id) {
		this.income_id = income_id;
	}
	public String getIncome_name() {
		return income_name;
	}
	public void setIncome_name(String income_name) {
		this.income_name = income_name;
	}

}
