package com.social.network.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserProfileImage;
import com.social.network.entity.UserProfileImageE;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.UserImageType;
import com.social.network.domain.UserProfile;
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
	public Optional<UserProfile> uploadUserProfileImage(Long userId, MultipartFile file) throws IOException
	{
		Files.createDirectories(Paths.get(UPLOAD_DIRECTORY));
		Optional<UserProfile> profile = userService.getUserbyId(userId);

		if (profile.isPresent())
		{
			UserProfile existingProfile = profile.get();
			try
			{
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
				Files.write(fileNameAndPath, file.getBytes());

				UserProfileImage userProfileImage = UserProfileImage.builder()
					.profile(existingProfile)
					.imageName(file.getOriginalFilename())
					.imageDescription(fileNameAndPath.toString())
					.userImageType(UserImageType.PROFILE_PIC)
					.createDateTime(LocalDateTime.now())
					.modifyDateTime(LocalDateTime.now())
					.build();

				imageRepo.save(ProfileMapper.convert(userProfileImage));

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
	public List<UserProfileImage> getUserProfileImages(Long userId) {
		List<UserProfileImageE> images = imageRepo.findByUserId(userId);
		return images.stream().map(ProfileMapper::convert).toList();
	}

	@Override
	public UserProfileImage getUserProfileImage(Long imageId, Long userId) {
		UserProfileImageE image = imageRepo.findByIdAndUserId(imageId, userId);
		return ProfileMapper.convert(image);
	}
}
