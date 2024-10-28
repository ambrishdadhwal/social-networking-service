package com.social.network.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.social.network.domain.Country;
import com.social.network.domain.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ProfileE.TABLE_NAME, uniqueConstraints =
{@UniqueConstraint(columnNames = "email")})
@SuperBuilder
@Slf4j
@Data
public class ProfileE
{

	protected final static String TABLE_NAME = "social_user";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String profileImage;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "social_user_id", referencedColumnName = "id")
	private Set<ProfileImageE> profileImages;

	@Column(name = "isActive", columnDefinition = "boolean default false")
	private Boolean isActive;

	@Column
	@Enumerated(EnumType.STRING)
	private Country country;

	@Column
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column
	private LocalDate dob;

	// after
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	// before
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Set<ProfileRoleE> userRoles;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createDateTime;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime modifiedDateTime;

	@PrePersist
	public void logNewUserAttempt()
	{
		log.info("Attempting to add new user with email:\n " + toString());
	}
}
