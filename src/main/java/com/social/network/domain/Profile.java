package com.social.network.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Profile
{

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private String password;
	private Country country;
	private Gender gender;
	private LocalDate dob;
	private Boolean isActive;
	private Set<String> roles;
	private String profileImage;
	private Set<ProfileImage> profileImages;
	private LocalDateTime createDateTime;
	private LocalDateTime modifiedDateTime;

}
