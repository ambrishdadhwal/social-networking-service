package com.social.network.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.social.network.domain.Country;
import com.social.network.domain.UserImageType;
import com.social.network.domain.UserProfile;
import com.social.network.domain.UserProfileImage;
import com.social.network.entity.UserProfileE;
import com.social.network.entity.UserProfileImageE;
import com.social.network.entity.UserProfileRoleE;
import com.social.network.presentation.CountryDTO;
import com.social.network.presentation.ImageTypeDTO;
import com.social.network.presentation.UserProfileDTO;
import com.social.network.presentation.UserProfileImageDTO;
import com.social.network.presentation.UserProfileLoginDTO;
import com.social.network.presentation.UserProfileUpdateDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProfileMapper
{

	public static UserProfileImageE convert(UserProfileImage n)
	{
		if (n == null)
		{
			return null;
		}

		return UserProfileImageE.builder()
			.id(n.getId())
			.user(ProfileMapper.convert(n.getProfile()))
			.imageName(n.getImageName())
			.imageDescription(n.getImageDescription())
			.userImageType(n.getUserImageType())
			.image(n.getImage())
			.isActive(n.getIsActive())
			.createDateTime(n.getCreateDateTime())
			.modifyDateTime(n.getModifyDateTime())
			.build();
	}

	public static UserProfileImage convert(UserProfileImageDTO n)
	{
		if (n == null)
		{
			return null;
		}

		return UserProfileImage.builder()
			.id(n.getId())
			.imageName(n.getImageName())
			.imageDescription(n.getImageDescription())
			.image(n.getImage())
			.isActive(n.getIsActive())
			.userImageType(UserImageType.valueOf(n.getImageType().toString()))
			.createDateTime(n.getCreateDate())
			.modifyDateTime(n.getModifyDate())
			.build();
	}

	public static UserProfileImageDTO convertDTO(UserProfileImage n)
	{
		if (n == null)
		{
			return null;
		}

		return UserProfileImageDTO.builder()
			.id(n.getId())
			.imageName(n.getImageName())
			.imageDescription(n.getImageDescription())
			.imageType(ImageTypeDTO.valueOf(n.getUserImageType().toString()))
			.image(n.getImage())
			.isActive(n.getIsActive())
			.createDate(n.getCreateDateTime())
			.modifyDate(n.getModifyDateTime())
			.build();
	}

	public static UserProfileImage convert(UserProfileImageE n)
	{
		if (n == null)
		{
			return null;
		}

		return UserProfileImage.builder()
			.id(n.getId())
			.imageName(n.getImageName())
			.imageDescription(n.getImageDescription())
			.userImageType(n.getUserImageType())
			.image(n.getImage())
			.isActive(n.getIsActive())
			.createDateTime(n.getCreateDateTime())
			.modifyDateTime(n.getModifyDateTime())
			.build();
	}

	public static UserProfile convert(UserProfileE from)
	{
		if (from == null)
		{
			return null;
		}

		Set<UserProfileImage> images = null;

		if (from.getProfileImages() != null)
		{
			images = from.getProfileImages().stream().map(ProfileMapper::convert).collect(Collectors.toSet());
		}

		return UserProfile.builder()
			.id(from.getId())
			.firstName(from.getFirstName())
			.lastName(from.getLastName())
			.email(from.getEmail())
			.password(from.getPassword())
			.country(from.getCountry())
			.gender(from.getGender())
			.dob(from.getDob())
			.profileImage(from.getProfileImage())
			.userProfileImages(images)
			.roles(from.getUserRoles().stream().map(UserProfileRoleE::getRole).collect(Collectors.toSet()))
			.createDateTime(from.getCreateDateTime())
			.modifiedDateTime(from.getModifiedDateTime())
			.isActive(from.getIsActive())
			.build();
	}

	public static UserProfileE convert(UserProfile from)
	{
		if (from == null)
		{
			return null;
		}

		Set<UserProfileRoleE> roles = new HashSet<>();

		from.getRoles().forEach(n -> {
			roles.add(UserProfileRoleE.builder().role(n).build());
		});

		Set<UserProfileImageE> images = null;

		if (from.getUserProfileImages() != null)
		{
			images = from.getUserProfileImages().stream().map(ProfileMapper::convert).collect(Collectors.toSet());
		}

		return UserProfileE.builder()
			.id(from.getId())
			.firstName(from.getFirstName())
			.lastName(from.getLastName())
			.email(from.getEmail())
			.password(from.getPassword())
			.country(from.getCountry())
			.gender(from.getGender())
			.dob(from.getDob())
			.userRoles(roles)
			.profileImage(from.getProfileImage())
			.profileImages(images)
			.createDateTime(from.getCreateDateTime())
			.modifiedDateTime(from.getModifiedDateTime())
			.isActive(from.getIsActive())
			.build();
	}

	public static UserProfile convert(UserProfileDTO from)
	{
		if (from == null)
		{
			return null;
		}

		Set<String> roles = new HashSet<>();
		roles.add("ADMIN");
		roles.add("USER");

		Set<UserProfileImage> images = null;

		if (from.getProfileImages() != null)
		{
			images = from.getProfileImages().stream().map(ProfileMapper::convert).collect(Collectors.toSet());
		}

		return UserProfile.builder()
			.id(from.getId())
			.firstName(from.getFirstName())
			.lastName(from.getLastName())
			.email(from.getEmail())
			.password(from.getPassword())
			.country(Country.getCountry(from.getCountry().getCountry()))
			.gender(from.getGender())
			.dob(from.getDob())
			.roles(roles)
			.profileImage(from.getProfileImage())
			.userProfileImages(images)
			.createDateTime(from.getCreateDateTime())
			.modifiedDateTime(from.getModifyDateTime())
			.isActive(from.getIsActive())
			.build();
	}

	public static UserProfileDTO convertDTO(UserProfile from)
	{
		if (from == null)
		{
			return null;
		}
		Set<UserProfileImageDTO> images = null;

		if (from.getUserProfileImages() != null)
		{
			images = from.getUserProfileImages().stream().filter(n->n.getUserImageType().equals(UserImageType.PROFILE_PIC)).map(ProfileMapper::convertDTO).collect(Collectors.toSet());
		}

		/*return ProfileDTO.builder()
			.id(from.getId())
			.firstName(from.getFirstName())
			.lastName(from.getLastName())
			.email(from.getEmail())
			.password(from.getPassword())
			.country(CountryDTO.getCountry(from.getCountry().toString()))
			.dob(from.getDob())
			.roles(from.getRoles())
			.profileImage(from.getProfileImage())
			.profileImages(images)
			.createDateTime(from.getCreateDateTime())
			.modifyDateTime(from.getModifiedDateTime())
			.isActive(from.getIsActive())
			.build();*/
		
		UserProfileDTO userProfileDTO =new UserProfileDTO();
		userProfileDTO.setId(from.getId());
		userProfileDTO.setFirstName(from.getFirstName());
		userProfileDTO.setLastName(from.getLastName());
		userProfileDTO.setEmail(from.getEmail());
		userProfileDTO.setPassword(from.getPassword());
		userProfileDTO.setCountry(CountryDTO.getCountry(from.getCountry().toString()));
		userProfileDTO.setDob(from.getDob());
		userProfileDTO.setRoles(from.getRoles());
		userProfileDTO.setProfileImage(from.getProfileImage());
		userProfileDTO.setProfileImages(images);
		userProfileDTO.setCreateDateTime(from.getCreateDateTime());
		userProfileDTO.setModifyDateTime(from.getModifiedDateTime());
		userProfileDTO.setIsActive(from.getIsActive());
		userProfileDTO.setGender(from.getGender());
		
		return userProfileDTO;
	}

	public static UserProfile convert(UserProfileLoginDTO from)
	{
		if (from == null)
		{
			return null;
		}
		return UserProfile.builder()
			.email(from.getEmail())
			.userName(from.getUserName())
			.password(from.getPassword())
			.build();
	}

	public static UserProfile convert(UserProfileUpdateDTO from)
	{
		if (from == null)
		{
			return null;
		}

		return UserProfile.builder()
			.id(from.getId())
			.firstName(from.getFirstName())
			.lastName(from.getLastName())
			.country(Country.getCountry(from.getCountry().toString()))
			.dob(from.getDob())
			.build();
	}

}
