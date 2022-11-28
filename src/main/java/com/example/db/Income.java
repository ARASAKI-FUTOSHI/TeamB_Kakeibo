package com.example.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Income {
   @Id
   @Column(name="id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer iid;
   private Integer user_id;
   private String income;
   private Integer deposit_date;
   private Integer money;
   private Integer bank_id;
   
public Integer getIid() {
	return iid;
}
public void setIid(Integer iid) {
	this.iid = iid;
}
public Integer getUser_id() {
	return user_id;
}
public void setUser_id(Integer user_id) {
	this.user_id = user_id;
}
public String getIncome() {
	return income;
}
public void setIncome(String income) {
	this.income = income;
}
public Integer getDeposit_date() {
	return deposit_date;
}
public void setDeposit_date(Integer deposit_date) {
	this.deposit_date = deposit_date;
}
public Integer getMoney() {
	return money;
}
public void setMoney(Integer money) {
	this.money = money;
}
public Integer getBank_id() {
	return bank_id;
}
public void setBank_id(Integer bank_id) {
	this.bank_id = bank_id;
}

}
