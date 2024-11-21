package com.social.network.entity;

import java.time.LocalDateTime;

import com.social.network.domain.UserImageType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "social_user_image")
@SuperBuilder
public class UserProfileImageE
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne()
	@JoinColumn(name = "social_user_id")
	private UserProfileE user;

	@ManyToOne()
	@JoinColumn(name = "social_post_id")
	private UserPostE post;

	@Column
	private String imageName;

	@Column
	private Boolean isActive;

	@Column
	private String imageDescription;

	@Column
	@Enumerated(EnumType.STRING)
	private UserImageType userImageType;

	@Column
	private byte[] image;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDateTime;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifyDateTime;
}
