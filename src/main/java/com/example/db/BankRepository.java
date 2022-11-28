package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM Bank ubs where ubs.user_id = :user_id")
	public List<Bank> userSelect(Integer user_id);
}
