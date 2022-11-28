package com.example.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IncomeView {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private Integer user_id;
   private String income;
   private Integer deposit_date;
   private Integer money;
   private String bank;
   private Integer bank_id;
   private Date date;
   private Integer income_id;
   
   
   public Integer getIncome_id() {
	return income_id;
}
public void setIncome_id(Integer income_id) {
	this.income_id = income_id;
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
private Integer date_id;
   
   
	public Integer getId() {
		return id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public String getIncome() {
		return income;
	}
	public Integer getDeposit_date() {
		return deposit_date;
	}
	public Integer getMoney() {
		return money;
	}
	public String getBank() {
		return bank;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public void setDeposit_date(Integer deposit_date) {
		this.deposit_date = deposit_date;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public Integer getBank_id() {
		return bank_id;
	}
	public void setBank_id(Integer bank_id) {
		this.bank_id = bank_id;
	}
   
}
