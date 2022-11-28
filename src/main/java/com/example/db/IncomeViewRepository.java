package com.example.db;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeViewRepository extends JpaRepository<IncomeView, Integer> {
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM IncomeView ubs where ubs.user_id = :user_id")
	public List<IncomeView> userIncomeSelect(Integer user_id);
	
	@Query(value="SELECT ubs FROM IncomeView ubs WHERE ubs.date = :date AND ubs.user_id = :user_id")
	public List<IncomeView> userIncomeSelectA(Date date, Integer user_id);

}
