package com.social.network.service;

import java.io.IOException;
import java.util.Optional;

import com.social.network.domain.Profile;
import com.social.network.domain.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface IUserImageService
{

	Optional<Profile> uploadUserImage(ProfileImage image, MultipartFile file) throws IOException;
}
