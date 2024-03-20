package com.example.service;

import java.util.List;

import com.example.entity.UserCredentials;
import com.example.entity.UserInfo;
import com.example.exception.UserNotFoundException;

public interface UserInfoService {
	
	public String addUser(UserInfo data);
	
	public UserInfo getUserInfo(String emailId) throws UserNotFoundException;
	
	public List<UserInfo> showAllUsers();
	
	public String userLogin(UserCredentials info);

}
