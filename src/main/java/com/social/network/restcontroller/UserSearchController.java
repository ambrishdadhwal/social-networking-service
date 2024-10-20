package com.social.network.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.Profile;
import com.social.network.presentation.CommonResponse;
import com.social.network.presentation.ProfileDTO;
import com.social.network.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/search")
@RequiredArgsConstructor
public class UserSearchController
{

	private final IUserService userService;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping(value = "/{name}")
	public ResponseEntity<CommonResponse<List<ProfileDTO>>> getUserById(@PathVariable(required = true, name = "name") String name)
	{
		List<Profile> profiles = userService.getUsersSearchbyName(name);
		CommonResponse<List<ProfileDTO>> dto = new CommonResponse<>();
		dto.setData(profiles.stream().map(ProfileMapper::convertDTO).toList());
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
