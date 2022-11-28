package com.example.db;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DateRepository extends JpaRepository<Datedb, Integer> {
	//date重複チェック
	@Query(value="SELECT ds FROM Datedb ds WHERE ds.date = :date AND ds.user_id = :user_id")
	public List<Datedb> findByDate(Date date, Integer user_id);

}