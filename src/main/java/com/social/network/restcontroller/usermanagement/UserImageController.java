package com.social.network.restcontroller.usermanagement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserProfileImage;
import com.social.network.presentation.UserProfileImageDTO;
import com.social.network.security.RequestContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.UserProfile;
import com.social.network.presentation.UserProfileDTO;
import com.social.network.service.IUserImageService;
import com.social.network.service.ProfileException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserImageController
{

	private final IUserImageService imageService;

	private final RequestContextHolder requestContextHolder;

	@GetMapping(value = "{userId}/image/{imageId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserProfileImageDTO> getImage(@PathVariable(required = true) Long imageId, @PathVariable(required = true) Long userId)
	{
		UserProfileImage image = imageService.getUserProfileImage(imageId, userId);
		return new ResponseEntity<>(ProfileMapper.convertDTO(image), HttpStatus.OK);
	}

	@GetMapping(value = "{userId}/image/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<List<UserProfileImageDTO>> getAllImages(@PathVariable(required = true) Long userId)
	{
		List<UserProfileImage> images = imageService.getUserProfileImages(userId);

		return new ResponseEntity<>( images.stream().map(ProfileMapper::convertDTO).toList(), HttpStatus.OK);
	}

	@PostMapping(value = "/{userId}/image", produces = "application/json")
	public ResponseEntity<UserProfileDTO> uploadImage(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file,
													  @RequestParam("imageName") String imageName, HttpServletRequest httpRequest) throws ProfileException, IOException
	{

		if(!userId.equals(requestContextHolder.getContext().getUserId()))
		{
			throw new ProfileException("You can only upload image with Logged In User");
		}

		Optional<UserProfile> profile = imageService.uploadUserProfileImage(userId, file);

        return profile.map(value -> new ResponseEntity<>(ProfileMapper.convertDTO(value), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

	@DeleteMapping(value = "{userId}/image/{imageId}")
	public ResponseEntity<?> deleteImage(@PathVariable(required = true) String imageId, @PathVariable(required = true) String userId)
	{
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}