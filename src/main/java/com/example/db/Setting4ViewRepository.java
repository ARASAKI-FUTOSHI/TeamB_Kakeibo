package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Setting4ViewRepository extends JpaRepository<Setting4_View, Integer> {
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM Setting4_View ubs where ubs.user_id = :user_id")
	public List<Setting4_View> userSelect(Integer user_id);

}
