package com.apap.tutorial8.service;

import java.util.Optional;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
	UserRoleModel addUser(UserRoleModel user);
	public String encrypt (String password);
	Optional<UserRoleModel> getUserDetailById(long id);

	
}
