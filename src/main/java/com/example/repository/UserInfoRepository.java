package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	
	@Query("Select u from UserInfo u where u.userCredentials.emailId=:emailId")
	Optional<UserInfo> findUserByEmailId(String emailId);

}
