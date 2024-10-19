package com.social.network.entity;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "social_user_post")
@NamedQueries(
{@NamedQuery(query = "SELECT  u from UserPostE u where u.id =:postId", name = "UserPostE.findByPostId")})
@Slf4j
public class UserPostE
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_a_id")
	private ProfileE user;

	@Column
	private String postData;

	/*
	 * To save Child objects automatically.Don't use mappedBy attribute , use JoinColumn on both sides i.e OneToMany & ManyToOne
	 * */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "social_post_id", referencedColumnName = "id")
	private Set<ProfileImageE> images;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdTime;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedTime;

	@PrePersist
	public void logNewUserAttempt()
	{
		log.info("Attempting to add new user with username: " + user);
	}
}
