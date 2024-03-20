package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.UserCredentials;
import com.example.entity.UserInfo;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserCredentialsRepository;
import com.example.repository.UserInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private UserCredentialsRepository userCredentialsRepository;

	@Override
	public String addUser(UserInfo data) {
	    Optional<UserInfo> existingUser = userInfoRepository.findUserByEmailId(data.getUserCredentials().getEmailId());

	    if (existingUser.isPresent()) {
	    	log.info("User with emailId: " + data.getUserCredentials().getEmailId() + " already exists");
	    	
	        return "User with emailId: " + data.getUserCredentials().getEmailId() + " already exists";
	    } else {
	    	// first persist the user credentials before storing user info details
	    	
	    	UserCredentials userCred = data.getUserCredentials();
	    	userCredentialsRepository.save(userCred);
	    	
	    	data.setUserCredentials(userCred);
	        userInfoRepository.save(data);
	        log.info("User with emailId: "+data.getUserCredentials().getEmailId()+" added successfully");
	        
	        return "User with emailId: "+data.getUserCredentials().getEmailId()+" added successfully";
	    }
	}


	@Override
	public UserInfo getUserInfo(String emailId) throws UserNotFoundException {
		UserInfo opt = userInfoRepository.findUserByEmailId(emailId).orElseThrow(()->new UserNotFoundException("User with emailId: "+emailId+" doesnot exists"));
		return opt;
	}

	@Override
	public List<UserInfo> showAllUsers() {
		List<UserInfo> userData = userInfoRepository.findAll();
		log.info("Fetched all user details");
		return userData;
	}



	@Override
	public String userLogin(UserCredentials info) {
		String email = info.getEmailId();
		String password = info.getPassword();
		
		Optional<UserCredentials> opt = userCredentialsRepository.findUserByEmailId(email);
		if(opt.isPresent()) {
			UserCredentials userLoginDetails = opt.get();
			
			//Check if the provided password matches the stored password
			
			if(password.equals(userLoginDetails.getPassword())) {
				return "Login Successful";
			}else {
				
				return "Incorrect Password";
			}
		}else {
			log.error("User with emailId: " + email + " not found");
			
			 return "User with emailId: " + email + " not found";
		}
	}

}
