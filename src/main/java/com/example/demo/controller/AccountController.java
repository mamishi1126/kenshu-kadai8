package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	Account account;

	@Autowired
	CustomerRepository customerRepository;


	//ログイン画面を表示
	@GetMapping({ "/", "/login", "/logout" })
	public String index() {
		//セッション情報を全てクリアする
		session.invalidate();

		return "login";
	}

	//ログインを実行
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		
		//Optional<Customer>
		List<Customer> customerList = null;
		
		customerList = customerRepository.findByEmailAndPassword(email,password);
		
		
		//メールアドレスが空の場合にエラーとする
		if (email == null || email.length() == 0) {
			//login.htmlの"{message}"部分に出力するだけのため、@RequestParamでの宣言は不要
			model.addAttribute("message", "メールアドレスを入力してください");
			return "login";
			
		} else if(customerList.size() == 0) {
			model.addAttribute("message", "メールアドレスとパスワードが一致しませんでした");
			return "login";
			
		} else {
			
			Customer customer = customerList.get(0);
			
		//セッション管理されたアカウント情報に名前をセット
		account.setName(customer.getName());	
		account.setId(customer.getId());
		

		//「/items」へのリダイレクト
		return "redirect:/items";
		
		}
	}

	//アカウント登録画面を表示
	@GetMapping("/account")
	public String create() {
		return "accountForm";
	}

	//アカウント登録処理実行
	@PostMapping("/account")
	public String store(
			@RequestParam("name") String name,
			@RequestParam("address") String address,
			@RequestParam("tel") String tel,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {

		ArrayList<String> errors = new ArrayList<String>();
		Customer customer = new Customer(name, address, tel, email, password);

		if (name.equals("")) {
			errors.add("名前は必須です");
		}
		
		if (address.equals("") ) {
			errors.add("住所は必須です");
		}
		
		if (tel.equals("")) {
			errors.add("電話番号は必須です");
		}

		if (email.equals("")) {
			errors.add("メールアドレスは必須です");
			
		} else if (email.equals(account.getEmail())) {
			errors.add("登録済みのメールアドレスです");
		}

		if (password.equals("")) {
			errors.add("パスワードは必須です");
			
		}
		

		if (errors.size() == 0) {
			

			customer.setName(name);
			customer.setAddress(address);
			customer.setTel(tel);
			customer.setEmail(email);
			customer.setPassword(password);
			
			customerRepository.save(customer);

			return "redirect:/login";

		} else {
			
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			model.addAttribute("password",password);
			model.addAttribute("errors", errors);
			
		
			return "accountForm";
		}

	}
}
