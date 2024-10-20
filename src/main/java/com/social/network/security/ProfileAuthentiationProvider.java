package com.social.network.security;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileAuthentiationProvider implements AuthenticationProvider
{

	private final CustomUserDetailsService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String username = authentication.getName();
		String password = (String)authentication.getCredentials();

		UserDetails user = userService.loadUserByUsername(username);

		if (user == null || !user.getUsername().equalsIgnoreCase(username))
		{
			throw new BadCredentialsException(username.concat(" - Username not found."));
		}

		if (!password.equals(user.getPassword()))
		{
			throw new BadCredentialsException("Wrong password.");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication)
	{
		return true;
	}

}
