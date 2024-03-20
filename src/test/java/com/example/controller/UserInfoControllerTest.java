package com.example.controller;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.entity.UserCredentials;
import com.example.entity.UserInfo;
import com.example.exception.UserNotFoundException;
import com.example.service.UserInfoService;

@SpringBootTest
public class UserInfoControllerTest {

	@Mock
	private UserInfoService userInfoService;

	@InjectMocks
	private UserInfoController userInfoController;

	@Test
	void testAddNewUser() {
		UserInfo userInfo = createUserDetails();
		when(userInfoService.addUser(userInfo)).thenReturn("User added successfully");

		ResponseEntity<String> response = userInfoController.addNewUser(userInfo);

		verify(userInfoService, times(1)).addUser(userInfo);
		//assert response.getBody().equals("User with emailId: "+userInfo.getUserCredentials().getEmailId()+" added successfully");
	}

	@Test
	void testGetUserDetails() throws UserNotFoundException {
		String emailId = "test@example.com";
		UserInfo userInfo = createUserDetails();
		when(userInfoService.getUserInfo(emailId)).thenReturn(userInfo);

		ResponseEntity<UserInfo> response = userInfoController.getUserDetails(emailId);

		verify(userInfoService, times(1)).getUserInfo(emailId);
		assert response.getStatusCode() == HttpStatus.OK;
		assert response.getBody().equals(userInfo);
	}

	@Test
	void testGetAllUsers() {
		List<UserInfo> users = Arrays.asList(createUserDetails(), createUserDetails());
		when(userInfoService.showAllUsers()).thenReturn(users);

		ResponseEntity<List<UserInfo>> response = userInfoController.getAllUsers();

		verify(userInfoService, times(1)).showAllUsers();
		assert response.getStatusCode() == HttpStatus.OK;
		assert response.getBody().equals(users);
	}

	@Test
	void testExistingUser() {
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmailId("nithes31@example.com");
        userCredentials.setPassword("Nike@3123");
        
		when(userInfoService.userLogin(userCredentials)).thenReturn("Login Successful");

		ResponseEntity<String> response = userInfoController.existingUser(userCredentials);

		verify(userInfoService, times(1)).userLogin(userCredentials);
		assert response.getStatusCode() == HttpStatus.OK;
		assert response.getBody().equals("Login Successful");
	}
	
	private UserInfo createUserDetails() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(1);
		userInfo.setFirstName("Ravi");
		userInfo.setLastName("Teja");
		userInfo.setMobileNum("9923382183");
		
		UserCredentials cred = new UserCredentials();
		cred.setUserId(1);
		cred.setEmailId("ravi@gmail.com");
		cred.setPassword("Raviteja@123");
		
		userInfo.setUserCredentials(cred);
		
		return userInfo;
	}
}
