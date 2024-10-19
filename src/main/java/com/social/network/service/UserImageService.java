package com.social.network.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.ImageType;
import com.social.network.domain.Profile;
import com.social.network.domain.ProfileImage;
import com.social.network.repository.ProfileImageRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserImageService implements IUserImageService
{

	private final IUserService userService;

	private final ProfileImageRepo imageRepo;

	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

	@Override
	public Optional<Profile> uploadUserImage(ProfileImage image, MultipartFile file) throws IOException
	{
		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
		Optional<Profile> profile = userService.getUserbyId(image.getUserId());

		if (profile.isPresent())
		{
			Profile existingProfile = profile.get();
			try
			{
				StringBuilder fileNames = new StringBuilder();
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
				fileNames.append(file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());

				ProfileImage profileImage = ProfileImage.builder()
					.profile(existingProfile)
					.imageName(file.getOriginalFilename())
					.imageDescription(fileNameAndPath.toString())
					.imageType(ImageType.PROFILE_PIC)
					.createDateTime(LocalDateTime.now())
					.modifyDateTime(LocalDateTime.now())
					.build();

				imageRepo.save(ProfileMapper.convert(profileImage));

				return userService.getUserbyId(existingProfile.getId());
			}
			catch (Exception e)
			{
				log.error("Some thing went wrong while saving the image : {} ", e.getLocalizedMessage());
			}
		}
		return Optional.empty();
	}
}
