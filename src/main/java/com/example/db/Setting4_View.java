package com.example.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Setting4_View {
	   @Id
	   @Column(name="id")
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Integer s4id;
	   private Integer user_id;
	   private String name;
	   private Integer date;
	   private Integer budget;
	   private String payment_methods;
	   private Integer payment_id;

	public Integer getS4id() {
		return s4id;
	}
	public void setS4id(Integer s4id) {
		this.s4id = s4id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public Integer getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}
}
