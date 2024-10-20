package com.social.network.restcontroller;

import java.io.IOException;
import java.util.Optional;

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
import com.social.network.domain.Profile;
import com.social.network.domain.ProfileImage;
import com.social.network.presentation.ProfileDTO;
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

	@GetMapping(value = "{userId}/image/{imageId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getImage(@PathVariable(required = true) String imageId, @PathVariable(required = true) String userId)
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "{userId}/image/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> getAllImages(@PathVariable(required = true) String userId)
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(value = "/{userId}/image", produces = "application/json")
	public ResponseEntity<ProfileDTO> uploadImage(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file,
		@RequestParam("imageName") String imageName, HttpServletRequest httpRequest) throws ProfileException, IOException
	{
		Profile hhtpProfile = (Profile)httpRequest.getAttribute("CurrentUser");
		if(!userId.equals(hhtpProfile.getId()))
		{
			throw new ProfileException("You can only create post with Logged In User");
		}
		
		ProfileImage image = ProfileImage.builder().userId(userId).build();

		Optional<Profile> profile = imageService.uploadUserImage(image, file);

		if (profile.isPresent())
		{
			return new ResponseEntity<>(ProfileMapper.convertDTO(profile.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "{userId}/image/{imageId}")
	public ResponseEntity<?> deleteImage(@PathVariable(required = true) String imageId, @PathVariable(required = true) String userId)
	{
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}