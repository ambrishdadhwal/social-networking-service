package com.social.network.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.social.network.domain.Profile;
import com.social.network.domain.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface IUserImageService
{

	Optional<Profile> uploadUserProfileImage(Long userId, MultipartFile file) throws IOException;

	List<ProfileImage> getUserProfileImages(Long userId);

	ProfileImage getUserProfileImage(Long userId, Long imageId);
}
