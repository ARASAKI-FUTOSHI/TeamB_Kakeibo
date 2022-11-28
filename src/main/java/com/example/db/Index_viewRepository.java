package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Index_viewRepository extends JpaRepository<Index_view, Integer> {
	public List<Index_view>  findByName(String str);
	//↓要確認
	//public List<Index_view>  findBy(String str);
	@Query(value="select iv from Index_view iv where iv.name is not null and iv.user_id = :user_id")
	public List<Index_view> myQueryName(Integer user_id);
	
	@Query(value="select iv from Index_view iv where iv.income is not null and iv.user_id = :user_id")
	public List<Index_view> myQueryIncome(Integer user_id);
	
	// SELECT name , sum(price) FROM index_view WHERE income is not null AND user_id = :user_id GROUP BY name;
	@Query(value="select iv from Index_view iv where iv.income is not null and iv.user_id = :user_id GROUP BY iv.income")
	public List<Index_view> myQueryIncomeSum(Integer user_id);
	
	@Query(value="SELECT ubs FROM Index_view ubs where ubs.user_id = :user_id")
	public List<Index_view> userSelect(Integer user_id);

}
