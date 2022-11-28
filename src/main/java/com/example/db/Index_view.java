package com.example.db;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Index_view {
	@Id
	private Integer id;
	private Integer user_id;
	private Date date;
	private Integer category_id;
	private String name;
	private Integer income_id;
	private String income;
	private Integer price;
	

	public Integer getId() {
		return id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public Date getDate() {
		return date;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public String getName() {
		return name;
	}
	public Integer getIncome_id() {
		return income_id;
	}
	public String getIncome() {
		return income;
	}
	public Integer getPrice() {
		return price;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIncome_id(Integer income_id) {
		this.income_id = income_id;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
}