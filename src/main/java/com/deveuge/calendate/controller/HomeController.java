package com.deveuge.calendate.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deveuge.calendate.enums.Alert;
import com.deveuge.calendate.model.service.UserService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.utils.ViewUtils;
import com.deveuge.calendate.view.dto.UserDTO;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String index(Model model) {
		return SecurityUtils.isAuthenticated() ? "dashboard" : "index";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required = false) Optional<String> error, RedirectAttributes redir, Model model) {
		if (SecurityUtils.isAuthenticated()) {
			return "redirect:/";
		}
		if(error.isPresent()) {
			ViewUtils.createAlert(redir, Alert.ERROR, "user.login.error");
			return "redirect:/login";
		}
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new UserDTO());
		}
		return "login";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, RedirectAttributes redir) {
		result = checkIfAlreadyRegistered(userDTO, result);

		if (result.hasErrors()) {
			redir.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			redir.addFlashAttribute("user", userDTO);
	        redir.addFlashAttribute("register", true);
			return "redirect:/login";
	    }
		
		userService.save(userService.convert(userDTO));
		
		ViewUtils.createAlert(redir, Alert.SUCCESS, "user.success.create");
		return "redirect:/login";
	}
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
	/**
	 * Check if user is already registered by username or email
	 * @param user
	 * @param result
	 * @return
	 */
	private BindingResult checkIfAlreadyRegistered(UserDTO user, BindingResult result) {
		boolean repeatedUsername = userService.findOne(user.getUsername()) != null;
		boolean repeatedEmail = userService.findByEmail(user.getEmail()) != null;
		boolean passwordsMatch = user.getPassword().equals(user.getRepeatPassword());
		
		if(repeatedUsername || repeatedEmail || !passwordsMatch) {
			result = new BeanPropertyBindingResult(user, "user");
			if(repeatedUsername)
				result.reject("Repeated.user.username");
			if(repeatedEmail)
				result.reject("Repeated.user.email");
			if(!passwordsMatch)
				result.reject("NotMatch.user.password");
		}
		
		return result;
	}
	
}
