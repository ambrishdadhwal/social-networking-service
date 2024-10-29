package com.social.network.security;

import java.util.Optional;

import com.social.network.domain.Profile;
import com.social.network.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{

	final IUserService profileService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		log.info("Inside CustomUserDetailsService.loadUserByUsername .....");
		Optional<Profile> currentUser = profileService.getUserbyEmail(username);

		if (currentUser.isPresent())
		{
			Profile user = currentUser.get();
			return UserPrincipal.create(user);
		}

		throw new UsernameNotFoundException("User not found with username :-" + username);
	}

	public UserPrincipal loadUserByUsername(String username, HttpServletRequest request) throws UsernameNotFoundException
	{
		log.info("Inside CustomUserDetailsService.loadUserByUsername .....");
		Optional<Profile> currentUser = profileService.getUserbyEmail(username);

		if (currentUser.isPresent())
		{
			//request.setAttribute("CurrentUser", currentUser.get());
			Profile user = currentUser.get();

			return UserPrincipal.create(user);
		}
		throw new UsernameNotFoundException("User not found with username :-" + username);
	}
}
