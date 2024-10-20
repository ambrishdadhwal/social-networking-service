package com.social.network.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter
{

	private final JwtTokenUtil jwtTokenUtil;

	private final CustomUserDetailsService customUserDetailsService;

	private  final RequestContextHolder requestContextHolder;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
	{
		try
		{
			final String requestTokenHeader = request.getHeader("Authorization");

			String username = null;
			String jwtToken = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
			{
				jwtToken = requestTokenHeader.substring(7);
				try
				{
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				}
				catch (IllegalArgumentException e)
				{
					logger.warn("Unable to get JWT Token");
				}
				catch (ExpiredJwtException e)
				{
					logger.warn("JWT Token has expired");
				}
			}
			else
			{
				logger.warn("JWT Token does not begin with Bearer String");
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username, request);

				if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails)))
				{
					UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(token);
				}

				/*ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
				RequestContext customContext = new RequestContext();
				customContext.setEmail(userDetails.getUsername());
				customContext.setToken(jwtToken);
				customContext.setAuthorities(userDetails.getAuthorities());
				attributes.setAttribute("context", customContext, RequestAttributes.SCOPE_REQUEST);
				RequestContextHolder.setRequestAttributes(attributes);*/

				RequestContext customContext = new RequestContext();
				customContext.setEmail(userDetails.getUsername());
				customContext.setToken(jwtToken);
				customContext.setAuthorities(userDetails.getAuthorities());
				requestContextHolder.setContext(customContext);

			}
			chain.doFilter(request, response);
		}
		catch (Exception e)
		{
			logger.error("Could not set user authentication in security context", e);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			response.getOutputStream().println(
				"{ "
					+ "\"error\": \"" + e.getMessage() + "\",\n "
					+ "\"time\": \"" + LocalDateTime.now() + "\",\n"
					+ "\"url\": \"" + request.getRequestURI() + "\",\n "
					+ "}");

		}
	}

}
