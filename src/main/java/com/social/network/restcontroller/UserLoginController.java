package com.social.network.restcontroller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.Profile;
import com.social.network.presentation.ProfileDTO;
import com.social.network.presentation.ProfileLoginDTO;
import com.social.network.security.JwtTokenUtil;
import com.social.network.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserLoginController
{

	final IUserService userService;

	final JwtTokenUtil jwtTokenUtil;

	@PostMapping(value = "/")
	public ResponseEntity<ProfileDTO> login(@RequestBody @Validated ProfileLoginDTO user) throws Exception
	{
		HttpStatus status = HttpStatus.OK;

		Optional<Profile> existingUser = userService.getUser(ProfileMapper.convert(user));

		if (existingUser.isPresent())
		{
			return new ResponseEntity<>(ProfileMapper.convertDTO(existingUser.get()), status);
		}
		else
		{
			throw new UsernameNotFoundException("User not found");
		}
	}

	@PostMapping(value = "/token")
	public ResponseEntity<JwtResponse> getUserToken(@RequestBody @Validated ProfileLoginDTO user) throws Exception
	{
		HttpStatus status = HttpStatus.OK;

		Optional<Profile> existingUser = userService.getUser(ProfileMapper.convert(user));

		if (existingUser.isPresent())
		{
			final String token = jwtTokenUtil.generateToken(existingUser.get());
			return new ResponseEntity<>(new JwtResponse("Bearer ".concat(token)), status);
		}
		else
		{
			throw new UsernameNotFoundException("User not found");
		}
	}
}
