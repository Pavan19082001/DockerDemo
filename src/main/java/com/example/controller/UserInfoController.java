package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserCredentials;
import com.example.entity.UserInfo;
import com.example.exception.UserNotFoundException;
import com.example.service.UserInfoService;


@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserInfoController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@PostMapping("/register")
	public ResponseEntity<String> addNewUser(@RequestBody UserInfo info){
		String s = userInfoService.addUser(info);
		return ResponseEntity.status(HttpStatus.CREATED).body(s);
	}
	
	@GetMapping("/getUser/{emailId}")
	public ResponseEntity<UserInfo> getUserDetails(@PathVariable String emailId) throws UserNotFoundException{
		return new ResponseEntity<UserInfo>(userInfoService.getUserInfo(emailId), HttpStatus.OK);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<List<UserInfo>> getAllUsers(){
		return new ResponseEntity<List<UserInfo>>(userInfoService.showAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> existingUser(@RequestBody UserCredentials info){
		return new ResponseEntity<String>(userInfoService.userLogin(info), HttpStatus.OK);
	}

}