package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM Payment ubs where ubs.user_id = :user_id")
	public List<Payment> userSelect(Integer user_id);

}