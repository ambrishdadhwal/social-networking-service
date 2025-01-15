package com.social.network.utils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import com.social.network.domain.UserImageType;
import com.social.network.domain.UserProfile;
import com.social.network.domain.UserProfileImage;
import com.social.network.domain.UserPost;
import com.social.network.entity.postgres.UserProfileE;
import com.social.network.entity.postgres.UserProfileImageE;
import com.social.network.entity.postgres.UserPostE;
import com.social.network.presentation.ImageTypeDTO;
import com.social.network.presentation.UserProfileImageDTO;
import com.social.network.presentation.UserUserPostDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserPostMapper
{

	public static UserPost convert(UserUserPostDTO from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserPost.builder().userId(from.getUserId())
			.post(from.getPost())
			.images(from.getImages().stream().map(UserPostMapper::convert).collect(Collectors.toSet()))
			.build();
	}

	public static UserProfileImage convert(UserProfileImageDTO from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserProfileImage.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.createDateTime(from.getCreateDate())
			.userImageType(UserImageType.valueOf(from.getImageType().toString()))
			.modifyDateTime(from.getModifyDate())
			.build();
	}

	public static UserProfileImageDTO convertImageDTO(UserProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserProfileImageDTO.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.createDate(from.getCreateDateTime())
			.imageType(ImageTypeDTO.valueOf(from.getUserImageType().toString()))
			.modifyDate(from.getModifyDateTime())
			.build();
	}

	public static UserProfileImage convert(UserProfileImageE from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserProfileImage.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.userImageType(from.getUserImageType())
			.createDateTime(from.getCreateDateTime())
			.modifyDateTime(from.getModifyDateTime())
			.build();
	}

	public static UserProfileImageE convert(UserProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserProfileImageE.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.userImageType(from.getUserImageType())
			.imageDescription(from.getImageDescription())
			.createDateTime(from.getCreateDateTime())
			.modifyDateTime(from.getModifyDateTime())
			.build();
	}

	public static UserProfileImageDTO convertDTO(UserProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserProfileImageDTO.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageType(ImageTypeDTO.valueOf(from.getUserImageType().toString()))
			.imageDescription(from.getImageDescription())
			.createDate(from.getCreateDateTime())
			.modifyDate(from.getModifyDateTime())
			.build();
	}

	public static UserUserPostDTO convert(UserPost from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		UserProfile profile = from.getUser();

		return UserUserPostDTO.builder()
			.id(from.getId())
				.userId(profile.getId())
			//.user(ProfileMapper.convertDTO(profile))
			.post(from.getPost())
			.images(from.getImages().stream().filter(n->n.getUserImageType().equals(UserImageType.POST_PIC)).map(UserPostMapper::convertImageDTO).collect(Collectors.toSet()))
			.createdTime(from.getCreatedTime())
			.modifiedTime(from.getModifiedTime())
			.build();
	}

	public static UserPostE convertEntity(UserPost from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return UserPostE.builder()
			.postData(from.getPost())
			.images(from.getImages().stream().map(UserPostMapper::convert).collect(Collectors.toSet()))
			.createdTime(LocalDateTime.now())
			.modifiedTime(LocalDateTime.now())
			.build();
	}

	public static UserPost convert(UserPostE from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		UserProfileE profile = from.getUser();

		return UserPost.builder()
			.id(from.getId())
			.user(ProfileMapper.convert(profile))
			.post(from.getPostData())
			.images(from.getImages().stream().map(UserPostMapper::convert).collect(Collectors.toSet()))
			.createdTime(from.getCreatedTime())
			.modifiedTime(from.getModifiedTime())
			.build();
	}
}
