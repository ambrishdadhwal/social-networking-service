package com.social.network.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserProfile;
import com.social.network.domain.UserProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface IUserImageService
{

	Optional<UserProfile> uploadUserProfileImage(Long userId, MultipartFile file) throws IOException;

	List<UserProfileImage> getUserProfileImages(Long userId);

	UserProfileImage getUserProfileImage(Long userId, Long imageId);
}
