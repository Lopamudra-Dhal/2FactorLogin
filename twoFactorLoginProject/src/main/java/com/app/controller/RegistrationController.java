package com.app.controller;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.model.User;
import com.app.service.SecurityService;
import com.app.service.UserService;
import com.app.validation.UserValidator;

@Controller
public class RegistrationController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		
		return "registration";
	}

	@RequestMapping(value="/registration",method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,final HttpServletRequest request, final Model model) throws UnsupportedEncodingException {
		userValidator.validate(userForm, bindingResult);
		Locale locale = request.getLocale();
		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		 if (userForm.isUsing2FA()) {
	            model.addAttribute("qr", userService.generateQRUrl(userForm));
	            return "redirect:/registration?lang=" + model+locale.getLanguage();
	        }
		securityService.autoLogin(userForm.getUserName(), userForm.getPasswordConfirm());

		 model.addAttribute(
		          "message", "Your account verified successfully");
		        return "redirect:/login?lang=" ;
	}
	
}
