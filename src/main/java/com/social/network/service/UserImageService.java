package com.social.network.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.social.network.entity.ProfileImageE;
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
	public Optional<Profile> uploadUserProfileImage(Long userId, MultipartFile file) throws IOException
	{
		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
		Optional<Profile> profile = userService.getUserbyId(userId);

		if (profile.isPresent())
		{
			Profile existingProfile = profile.get();
			try
			{
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
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

	@Override
	public List<ProfileImage> getUserProfileImages(Long userId) {
		List<ProfileImageE> images = imageRepo.findByUserId(userId);
		return images.stream().map(ProfileMapper::convert).toList();
	}

	@Override
	public ProfileImage getUserProfileImage(Long imageId, Long userId) {
		ProfileImageE image = imageRepo.findByIdAndUserId(imageId, userId);
		return ProfileMapper.convert(image);
	}
}
