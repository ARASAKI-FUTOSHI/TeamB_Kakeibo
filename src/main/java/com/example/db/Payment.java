package com.example.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pid;
	private Integer user_id;
	private String payment_methods;
	private Integer start_date;
	private Integer closing_date;
	private Integer debit_date;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getPayment_methods() {
		return payment_methods;
	}
	public void setPayment_methods(String payment_methods) {
		this.payment_methods = payment_methods;
	}
	public Integer getStart_date() {
		return start_date;
	}
	public void setStart_date(Integer start_date) {
		this.start_date = start_date;
	}
	public Integer getClosing_date() {
		return closing_date;
	}
	public void setClosing_date(Integer closing_date) {
		this.closing_date = closing_date;
	}
	public Integer getDebit_date() {
		return debit_date;
	}
	public void setDebit_date(Integer debit_date) {
		this.debit_date = debit_date;
	}

	
}