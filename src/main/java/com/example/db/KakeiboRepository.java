package com.example.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KakeiboRepository extends JpaRepository<Kakeibo, Integer> {
	//名前重複チェックの為の名前だけだすやつ
	public List<Kakeibo> findByName(String str);
	
	@Query(value="select a from Kakeibo a where a.name = :name and a.pass = :pass")
	public List<Kakeibo>findByNamePass(@Param("name")String name,@Param("pass")String pass);

}

