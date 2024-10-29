package com.social.network.utils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

import com.social.network.domain.ImageType;
import com.social.network.domain.Profile;
import com.social.network.domain.ProfileImage;
import com.social.network.domain.UserPost;
import com.social.network.entity.ProfileE;
import com.social.network.entity.ProfileImageE;
import com.social.network.entity.UserPostE;
import com.social.network.presentation.ImageTypeDTO;
import com.social.network.presentation.ProfileImageDTO;
import com.social.network.presentation.UserPostDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserPostMapper
{

	public static UserPost convert(UserPostDTO from)
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

	public static ProfileImage convert(ProfileImageDTO from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return ProfileImage.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.createDateTime(from.getCreateDate())
			.imageType(ImageType.valueOf(from.getImageType().toString()))
			.modifyDateTime(from.getModifyDate())
			.build();
	}

	public static ProfileImageDTO convertImageDTO(ProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return ProfileImageDTO.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.createDate(from.getCreateDateTime())
			.imageType(ImageTypeDTO.valueOf(from.getImageType().toString()))
			.modifyDate(from.getModifyDateTime())
			.build();
	}

	public static ProfileImage convert(ProfileImageE from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return ProfileImage.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageDescription(from.getImageDescription())
			.imageType(from.getImageType())
			.createDateTime(from.getCreateDateTime())
			.modifyDateTime(from.getModifyDateTime())
			.build();
	}

	public static ProfileImageE convert(ProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return ProfileImageE.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageType(from.getImageType())
			.imageDescription(from.getImageDescription())
			.createDateTime(from.getCreateDateTime())
			.modifyDateTime(from.getModifyDateTime())
			.build();
	}

	public static ProfileImageDTO convertDTO(ProfileImage from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		return ProfileImageDTO.builder()
			.id(from.getId())
			.imageName(from.getImageName())
			.imageType(ImageTypeDTO.valueOf(from.getImageType().toString()))
			.imageDescription(from.getImageDescription())
			.createDate(from.getCreateDateTime())
			.modifyDate(from.getModifyDateTime())
			.build();
	}

	public static UserPostDTO convert(UserPost from)
	{
		if (!Objects.nonNull(from))
		{
			return null;
		}

		Profile profile = from.getUser();

		return UserPostDTO.builder()
			.id(from.getId())
				.userId(profile.getId())
			//.user(ProfileMapper.convertDTO(profile))
			.post(from.getPost())
			.images(from.getImages().stream().filter(n->n.getImageType().equals(ImageType.POST_PIC)).map(UserPostMapper::convertImageDTO).collect(Collectors.toSet()))
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

		ProfileE profile = from.getUser();

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
