package com.social.network.domain;

import java.time.LocalDateTime;

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
public class UserProfileImage
{

	private Long id;
	private Long userId;
	private UserProfile profile;
	private String imageName;
	private String imageDescription;
	private byte[] image;
	private UserImageType userImageType;
	private Boolean isActive;
	private LocalDateTime createDateTime;
	private LocalDateTime modifyDateTime;
}
