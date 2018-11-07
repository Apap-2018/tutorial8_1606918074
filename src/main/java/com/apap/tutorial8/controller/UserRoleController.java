package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping (value="/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user) {
		userService.addUser(user);
		return "home";
	}
	
	@RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	@ResponseBody
	public String changeUserPassword(
	  @RequestParam("password") String password, 
	  @RequestParam("oldpassword") String oldPassword,
	  @RequestParam("passwordConfirmation") String passwordConfirmation) {
		UserRoleModel activeUser = userService.getActiveUser();
		if(userService.confirmPassword(oldPassword, password, passwordConfirmation)) {
			userService.updateUserPassword(activeUser, password);
			return "home";
		}
		else return "change-password-failed";
	}
}
