package com.apap.tutorial8.service;

import java.util.Optional;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt (String password);
	Optional<UserRoleModel> getUserDetailById(long id);
	UserRoleModel getActiveUser();
	boolean confirmPassword(String oldPassword, String password, String passwordConfirmation);
	boolean confirmPassword(String password);
	void updateUserPassword(UserRoleModel activeUser, String password);

	
}
