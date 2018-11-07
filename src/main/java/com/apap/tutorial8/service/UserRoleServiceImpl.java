package com.apap.tutorial8.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;

	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public Optional<UserRoleModel> getUserDetailById(long id) {
		return userDb.findById(id);
	}
	
	@Override
	public UserRoleModel getActiveUser() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String uname;
	    
	    if(auth instanceof UserDetails) uname = ((UserDetails)auth).getUsername();
	    else uname=auth.toString();
	    
	    return userDb.findByUsername(uname);
	}

	@Override
	public boolean confirmPassword(String oldPassword, String password, String passwordConfirmation) {
		boolean flagOldPass = false;
		boolean flagPass = false;
		if(!encrypt(oldPassword).equals(getActiveUser().getPassword()))
			flagOldPass=true;
		if(password.equals(passwordConfirmation))
			flagPass=true;
			flagPass=this.confirmPassword(password);
		return (flagOldPass&&flagPass);
	}
	
	@Override
	public boolean confirmPassword(String password){
        boolean passwordLength = false;
        boolean passwordContainsDigit = false;
        boolean passwordContainsLetter = false;

        if (password.length() >= 8) passwordLength = true;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) passwordContainsDigit = true;
        }

        passwordContainsLetter = password.matches(".*[a-zA-Z]+.*");

        return (passwordLength&&passwordContainsDigit&&passwordContainsLetter);
    }

	@Override
	public void updateUserPassword(UserRoleModel activeUser, String password) {
		// TODO Auto-generated method stub
		
	}
	
	
}
