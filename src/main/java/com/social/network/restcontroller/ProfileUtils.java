package com.social.network.restcontroller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.social.network.presentation.ImageTypeDTO;
import com.social.network.presentation.ProfileDTO;
import com.social.network.presentation.ProfileImageDTO;
import com.social.network.presentation.ProfileLoginDTO;
import com.social.network.restcontroller.UserController;
import com.social.network.restcontroller.UserLoginController;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class ProfileUtils<T>
{

	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

	public static Set<ProfileImageDTO> createImages(MultipartFile[] files) throws IOException
	{
		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));

		Set<ProfileImageDTO> images = new HashSet<>();
		if (files != null)
		{
			try
			{
				for (MultipartFile file : files)
				{
					StringBuilder fileName = new StringBuilder();
					Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
					fileName.append(file.getOriginalFilename());
					Files.write(fileNameAndPath, file.getBytes());

					ProfileImageDTO postImage = ProfileImageDTO.builder()
						.imageName(file.getOriginalFilename())
						.imageDescription(fileNameAndPath.toString())
						.imageType(ImageTypeDTO.POST_PIC)
						.createDate(LocalDateTime.now())
						.modifyDate(LocalDateTime.now())
						.build();
					images.add(postImage);
				}
			}
			catch (Exception e)
			{
				log.error("Exception while saving image in local dir - {}", e.getLocalizedMessage());
			}
		}
		return images;
	}

	public static void addLinkToUser(ProfileDTO user)
	{
		try
		{
			user.add(linkTo(UserController.class).slash(user.getId()).withSelfRel());
			user.add(linkTo(methodOn(UserController.class).getUsers(0, 50)).withRel("users"));
			user.add(linkTo(methodOn(UserLoginController.class).getUserToken(ProfileLoginDTO.builder().build())).withRel("login-token"));
		}
		catch (Exception e)
		{
		}
	}
}
