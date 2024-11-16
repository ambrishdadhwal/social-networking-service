package com.social.network.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.social.network.presentation.CommonResponse;
import com.social.network.presentation.ProfileDTO;
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

import com.social.network.utils.UserPostMapper;
import com.social.network.domain.Profile;
import com.social.network.domain.UserPost;
import com.social.network.presentation.ProfileImageDTO;
import com.social.network.presentation.UserPostDTO;
import com.social.network.service.IPostService;
import com.social.network.service.IUserService;
import com.social.network.service.ProfileException;
import com.social.network.restcontroller.ProfileUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserPostController
{

	final IUserService profileService;

	final IPostService postService;

	private final RequestContextHolder requestContextHolder;

	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

	@GetMapping(value = "/{userId}/post")
	public ResponseEntity<List<UserPostDTO>> getAllPostsByUser(@PathVariable Long userId)
	{
		List<UserPost> userPosts = postService.getAllUserPost(userId);
		List<UserPostDTO> userPostDTOs = userPosts.stream().map(UserPostMapper::convert).toList();
		return new ResponseEntity<>(userPostDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/{userId}/post/{postId}")
	public ResponseEntity<UserPostDTO> getPostById(@PathVariable Long userId, @PathVariable Long postId)
	{
		UserPost userPost = postService.getUserPostByUserIdAndPostId(userId, postId);
		return new ResponseEntity<>(UserPostMapper.convert(userPost), HttpStatus.OK);
	}

	@PostMapping(value = "/{userId}/post", produces = "application/json")
	public ResponseEntity<UserPostDTO> addPostForUser(@PathVariable("userId") Long userId,
		@RequestParam(name = "post") String post,
		@RequestParam(name = "file", required = false) MultipartFile[] files, HttpServletRequest httpRequest) throws ProfileException, IOException
	{
		Long loggedInUserId = requestContextHolder.getContext().getUserId();
		if(!userId.equals(loggedInUserId))
		{
			throw new ProfileException("You can only create post with Logged In User");
		}
		Set<ProfileImageDTO> images = ProfileUtils.createImages(files);
		UserPostDTO request = UserPostDTO.builder()
			.userId(userId)
			.post(post)
			.images(images)
			.build();
		Optional<UserPost> result = postService.addUserPost(UserPostMapper.convert(request));
		if (result.isPresent())
		{
			return new ResponseEntity<>(UserPostMapper.convert(result.get()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping(value = "/{userId}/post/{postId}/")
	public ResponseEntity<CommonResponse> deletePost(@PathVariable Long userId, @PathVariable Long postId)
	{
		CommonResponse response =postService.deleteUserPost(userId, postId);
		return new ResponseEntity<>(response, response.getStatus());
	}

}
