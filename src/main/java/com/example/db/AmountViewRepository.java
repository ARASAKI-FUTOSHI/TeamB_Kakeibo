package com.example.db;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmountViewRepository extends JpaRepository<AmountView, Integer> {
	//id=category_idのリストだけ取得
	@Query(value="select cs from AmountView cs where cs.category_id = :id AND cs.user_id = :user_id")
	public List<AmountView> categorySelect(@Param("id") Integer id,Integer user_id);
	
	//ユーザーIDと日付が同じで、かつカテゴリーIDがNullではない物（支出のみ）
	@Query(value="SELECT ds FROM AmountView ds WHERE ds.date = :date AND ds.user_id = :user_id AND ds.category_id != Null")
	public List<AmountView> dateSelect(Date date, Integer user_id);

	//ユーザーIDと日付が同じで、かつインカムIDがNullではない物（収入のみ）
	@Query(value="SELECT ds FROM AmountView ds WHERE ds.date = :date AND ds.user_id = :user_id AND ds.income_id != Null")
	public List<AmountView> dateSelectIncome(Date date, Integer user_id);

	
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM AmountView ubs where ubs.user_id = :user_id")
	public List<AmountView> userSelect(Integer user_id);

}
