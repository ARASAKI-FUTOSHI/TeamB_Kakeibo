package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	//user_idが同じものだけ検索
	@Query(value="SELECT ubs FROM Category ubs where ubs.user_id = :user_id")
	public List<Category> userSelect(Integer user_id);

}

