package com.deveuge.calendate.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.deveuge.calendate.enums.Alert;
import com.deveuge.calendate.model.entity.User;
import com.deveuge.calendate.model.service.UserService;
import com.deveuge.calendate.utils.SecurityUtils;
import com.deveuge.calendate.utils.ViewUtils;
import com.deveuge.calendate.view.dto.UserDTO;

@Controller
public class SettingsController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/settings")
	public String settingsView(@RequestParam(required = false) String code, @RequestParam(required = false) Optional<String> disconnect, Model model, RedirectAttributes redir) {
		if (!model.containsAttribute("user")) {
			User user = userService.findOne(SecurityUtils.getCurrentUserUsername());
			model.addAttribute("user", userService.convert(user));
		}
		return "settings";
	}
	
	@PostMapping("/settings")
	public String settingsPost(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, RedirectAttributes redir, 
			HttpServletRequest request, HttpServletResponse response) {
		result = checkSettingsFormValidity(userDTO, result);
		
		if (result.hasErrors()) {
			redir.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
			userDTO.setUsername(SecurityUtils.getCurrentUserUsername());
			redir.addFlashAttribute("user", userDTO);
	    } else {
	    	userService.save(userService.convert(userDTO));
	    	LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	    	localeResolver.setLocale(request, response, Locale.forLanguageTag(userDTO.getLocale()));
			ViewUtils.createAlert(redir, Alert.SUCCESS, "user.success.update");
	    }
		
		return "redirect:/settings";
	}
	
	/**
	 * Check submitted settings form validity
	 * @param userDTO
	 * @param result
	 * @return
	 */
	private BindingResult checkSettingsFormValidity(UserDTO userDTO, BindingResult result) {
		if(StringUtils.hasText(userDTO.getPassword())) {
			if(!userDTO.getPassword().equals(userDTO.getRepeatPassword())) {
				result.reject("NotMatch.user.password");
			}
		} else {
			userDTO.setPassword(null);
			if (result.hasFieldErrors("password")) {
				userDTO.setPassword(null);
				List<FieldError> errorList = new ArrayList<>();
				for(FieldError error : result.getFieldErrors()) {
					if(!error.getField().equals("password")) {
						errorList.add(error);
					}
				}
				result = new BeanPropertyBindingResult(userDTO, "user");
				for (FieldError fieldError : errorList) {
		            result.addError(fieldError);
		        }
			}
		}
		return result;
	}
	
}