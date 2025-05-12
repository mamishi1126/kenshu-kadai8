package com.example.demo.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

/*
 * ログイン時の情報をセッションスコープ内に保持するAccountクラス
 * 出力のためだけのクラスであり、外部に流出したらまずい情報は格納しない
 * idから名前を出力する際等に使用
 */

@Component
@SessionScope
public class Account {
	
	private Integer id;
	private String name;
	private String email;
	
	
	public Account() {
		
	}
	
	public Account(Integer id,String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
}
