package com.start.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.start.domain.User;
import com.start.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("loginForm")
	public String loginForm() {
		return "/user/login";
	}
	
	@PostMapping("login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserid(userId);
		if(user == null) {
			System.out.println("id 실패");
			return "redirect:/users/loginForm";
		}
		
		if(!user.matchPassword(password)) {
			System.out.println("password 실패");
			return "redirect:/users/loginForm";
		}
		
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
		
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String signupForm() {
		return "/user/form";
	}

	@PostMapping("")
	public String signup(User user) {
		System.out.println("user  : " + user.toString());
		userRepository.save(user);		
		return "redirect:/users";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		if(HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessiondUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessiondUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		model.addAttribute("infoUser", user);
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
		if(HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		
		User sessiondUser = HttpSessionUtils.getUserFromSession(session);
		if(!sessiondUser.matchId(id)) {
			throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
		}
		
		User user = userRepository.findById(id).get();
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		System.out.println(userRepository.findAll().toString());
		return "/user/list";
	}
	

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
		return "redirect:/";
	}
	
}
