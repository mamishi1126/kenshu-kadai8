package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	//カテゴリーIDによる検索
	//select * from where category_id = ?
	List<Item> findByCategoryId(Integer categoryId);
	
}
