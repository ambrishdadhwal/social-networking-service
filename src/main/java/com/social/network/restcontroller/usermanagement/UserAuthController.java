package com.social.network.restcontroller.usermanagement;

import java.util.Optional;

import com.social.network.presentation.JwtResponse;
import com.social.network.service.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.social.network.utils.ProfileMapper;
import com.social.network.domain.UserProfile;
import com.social.network.presentation.UserProfileDTO;
import com.social.network.presentation.UserProfileLoginDTO;
import com.social.network.security.JwtTokenUtil;
import com.social.network.service.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserAuthController
{

	private final IUserService userService;

	private final JwtTokenUtil jwtTokenUtil;

	private final TokenBlacklistService tokenBlacklistService;

	@PostMapping(value = "/login")
	public ResponseEntity<UserProfileDTO> login(@RequestBody @Validated UserProfileLoginDTO user) throws Exception
	{
		HttpStatus status = HttpStatus.OK;

		Optional<UserProfile> existingUser = userService.getUser(ProfileMapper.convert(user));

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
	public ResponseEntity<JwtResponse> getUserToken(@RequestBody @Validated UserProfileLoginDTO user) throws Exception
	{
		HttpStatus status = HttpStatus.OK;

		Optional<UserProfile> existingUser = userService.getUser(ProfileMapper.convert(user));

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

	@PostMapping(value = "/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) throws Exception
	{
		String msg = "";
		tokenBlacklistService.blacklist(token);
		log.info("token successfully blacklisted : {}", token);

		if(tokenBlacklistService.isTokenBlacklisted(token)){
			log.info("token already blacklisted... : {}", token);
			msg = "You are successfully logout from system !";
		}else{
			msg = "Something went wrong !";
		}
		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
