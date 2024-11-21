package com.social.network.restcontroller.usermanagement;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.social.network.presentation.UserProfileDTO;

@Controller
@RequestMapping("/ui")
public class AuthController
{

	@GetMapping("/index")
	public String home()
	{
		return "index";
	}

	// handler method to handle user registration form request
	@GetMapping("/register")
	public String showRegistrationForm(Model model)
	{
		// create model object to store form data
		UserProfileDTO profile = new UserProfileDTO();
		model.addAttribute("profile", profile);
		return "register";
	}

	@GetMapping("/login")
	public String login()
	{
		return "login";
	}
}
