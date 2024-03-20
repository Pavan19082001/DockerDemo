package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "first_name")
	@NotBlank(message = "first name cannot be null")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "mobile_number")
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile Num should start with 6|7|8|9 and should exactly have 10 digits")
	private String mobileNum;
	
	@OneToOne
	@JoinColumn(name="email_id", referencedColumnName = "emailId")
	private UserCredentials userCredentials;                        // uni-directional mapping...
	
	@Column(name="user_type")
	@Enumerated(EnumType.STRING)
	private UserType userType;

}
