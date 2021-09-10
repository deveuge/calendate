package com.deveuge.calendate.utils;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.deveuge.calendate.enums.Alert;

public class ViewUtils {

	public static void createAlert(RedirectAttributes redir, Alert alert, String message) {
		redir.addFlashAttribute(Constants.ALERT_CLASS, alert.name());
		redir.addFlashAttribute(Constants.ALERT_MESSAGE, message);
	}
	
	public static String redirect404(RedirectAttributes redir) {
		ViewUtils.createAlert(redir, Alert.ERROR, "page.404.error");
		return "redirect:/";
	}
}
