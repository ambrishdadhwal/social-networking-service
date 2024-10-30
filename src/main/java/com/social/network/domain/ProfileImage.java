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
public class ProfileImage
{

	private Long id;
	private Long userId;
	private Profile profile;
	private String imageName;
	private String imageDescription;
	private byte[] image;
	private ImageType imageType;
	private Boolean isActive;
	private LocalDateTime createDateTime;
	private LocalDateTime modifyDateTime;
}
