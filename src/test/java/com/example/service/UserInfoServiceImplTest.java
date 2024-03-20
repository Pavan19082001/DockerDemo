package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.entity.UserCredentials;
import com.example.entity.UserInfo;
import com.example.exception.UserNotFoundException;
import com.example.repository.UserCredentialsRepository;
import com.example.repository.UserInfoRepository;

@SpringBootTest
public class UserInfoServiceImplTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @Test
    void testAddUser() {
        UserInfo userInfo = new UserInfo();
        UserCredentials userCredentials = new UserCredentials();
        userInfo.setUserCredentials(userCredentials);

        when(userInfoRepository.findUserByEmailId(any())).thenReturn(Optional.empty());
        when(userCredentialsRepository.save(any())).thenReturn(userCredentials);
        when(userInfoRepository.save(any())).thenReturn(userInfo);

        String result = userInfoService.addUser(userInfo);

        assertEquals("User with emailId: " + userCredentials.getEmailId() + " added successfully", result);
        verify(userCredentialsRepository, times(1)).save(any());
        verify(userInfoRepository, times(1)).save(any());
    }

    @Test
    void testAddUserExistingUser() {
        UserInfo userInfo = new UserInfo();
        UserCredentials userCredentials = new UserCredentials();
        userInfo.setUserCredentials(userCredentials);

        when(userInfoRepository.findUserByEmailId(any())).thenReturn(Optional.of(userInfo));

        String result = userInfoService.addUser(userInfo);

        assertEquals("User with emailId: " + userCredentials.getEmailId() + " already exists", result);
        verify(userCredentialsRepository, never()).save(any());
        verify(userInfoRepository, never()).save(any());
    }

    @Test
    void testGetUserInfo() throws UserNotFoundException {
        UserInfo userInfo = new UserInfo();
        String emailId = "test@example.com";

        when(userInfoRepository.findUserByEmailId(emailId)).thenReturn(Optional.of(userInfo));

        UserInfo result = userInfoService.getUserInfo(emailId);

        assertEquals(userInfo, result);
    }

    @Test
    void testGetUserInfoNotFound() {
        String emailId = "test@example.com";

        when(userInfoRepository.findUserByEmailId(emailId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userInfoService.getUserInfo(emailId));
    }

    @Test
    void testShowAllUsers() {
        // Mock data
        UserInfo user1 = new UserInfo();
        UserInfo user2 = new UserInfo();
        List<UserInfo> userList = List.of(user1, user2);

        when(userInfoRepository.findAll()).thenReturn(userList);

        List<UserInfo> result = userInfoService.showAllUsers();

        assertEquals(userList, result);
    }

    @Test
    void testUserLoginSuccessful() {
        String email = "test@example.com";
        String password = "password";

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmailId(email);
        userCredentials.setPassword(password);

        when(userCredentialsRepository.findUserByEmailId(email)).thenReturn(Optional.of(userCredentials));

        String result = userInfoService.userLogin(userCredentials);

        assertEquals("Login Successful", result);
    }

//    @Test
//    void testUserLoginIncorrectPassword() {
//        String email = "test@example.com";
//        String password = "password123";
//
//        UserCredentials userCredentials = new UserCredentials();
//        userCredentials.setEmailId(email);
//        userCredentials.setPassword("password");
//
//        when(userCredentialsRepository.findUserByEmailId(email)).thenReturn(Optional.of(userCredentials));
//
//        String result = userInfoService.userLogin(userCredentials);
//
//        assertEquals("Incorrect Password", result);
//    }

    @Test
    void testUserLoginUserNotFound() {
        String email = "test@example.com";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setEmailId(email);

        when(userCredentialsRepository.findUserByEmailId(email)).thenReturn(Optional.empty());

        String result = userInfoService.userLogin(userCredentials);

        assertEquals("User with emailId: " + email + " not found", result);
    }
}

