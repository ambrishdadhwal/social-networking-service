package com.social.network.security;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.social.network.domain.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable
{

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 105 * 60 * 60;

	@Value("${jwt.secret : RvuONj6zfL6iHn/toIbPUS6O1kx+Gr8c9it2bseLKCtbWOizM5CEoWcaJs529c48ToeHaJnVifnldTbmBI9qZSka7mNlRrUH14jVZEVcXIkhESsqc/T8SBNaPo1F65ty8Sw3cOVgIUQnbudYG4WkVB0mcMdUfU15wnwZ5kLO+5/P6cIRVuxpaXhjaYE+1mL2Ww/xDoxNONjysKeFBnbsVB6WDMNh+XRtoLqSCdJ7mpOtw6/I1iOqPDrYYcXNoIpobL7iy9bqk6JcefhqWZhd1++L68Ae62n612Hm/EuGOPYkqp0lmkmigXEzmjg0vHkslmbcvwAZMiKFin1DpSLFts+1Gywtz47pRuilaRxr97M=}")
	private String secret;

	// retrieve username from jwt token
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token)
	{
		return Jwts.parser() .setSigningKey(key()).build().parseSignedClaims(token).getPayload();

	}

	// check if the token has expired
	private Boolean isTokenExpired(String token)
	{
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(Profile userProfile)
	{
		Map<String, Object> claims = new HashMap<>();
		claims.put("userName", userProfile.getUserName());
		claims.put("activeFlag", userProfile.getIsActive());
		return generateToken(claims, userProfile);
	}

	public String generateToken(Map<String, Object> extraClaims, Profile userDetails)
	{
		return Jwts.builder()
			.claims()
			.add(extraClaims)
			.subject(userDetails.getEmail())
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
			.and()
			.signWith(key())
			.compact();
	}

	private Key key()
	{
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
