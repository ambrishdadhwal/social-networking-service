package com.social.network.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserPost
{

	private Long id;
	private Long userId;
	private Profile user;
	private String post;
	private Set<ProfileImage> images;
	private LocalDateTime createdTime;
	private LocalDateTime modifiedTime;
}
