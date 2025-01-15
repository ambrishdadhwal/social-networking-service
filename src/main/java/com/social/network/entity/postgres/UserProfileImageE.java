package com.social.network.entity.postgres;

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

	@ManyToOne
	@JoinColumn(name = "social_user_id")
	private UserProfileE user;

	@ManyToOne
	@JoinColumn(name = "social_post_id")
	private UserPostE post;

	@Column(name = "image_name")
	private String imageName;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "image_description")
	private String imageDescription;

	@Column(name = "user_image_type")
	@Enumerated(EnumType.STRING)
	private UserImageType userImageType;

	@Column
	private byte[] image;

	@Column(name = "create_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDateTime;

	@Column(name = "modify_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifyDateTime;
}
