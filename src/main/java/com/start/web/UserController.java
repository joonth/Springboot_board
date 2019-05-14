package com.start.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.start.domain.User;
import com.start.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/user/create")
	public String create(User user) {
		System.out.println("user  : " + user.toString());
		userRepository.save(user);		
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		System.out.println(userRepository.findAll().toString());
		return "/user/list";
	}
}
