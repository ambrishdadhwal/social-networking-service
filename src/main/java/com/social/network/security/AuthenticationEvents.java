package com.social.network.security;

import com.social.network.config.notification.ProfileNotificationDetails;
import com.social.network.config.notification.ProfileNotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEvents
{

	final ProfileNotificationService emailService;

	@Value("${spring.mail.username}")
	String userName;

	@EventListener
	public void onSuccess(AuthenticationSuccessEvent success)
	{
		ProfileNotificationDetails emailDetails = ProfileNotificationDetails.builder()
			.recipient(userName)
			.subject("Login Succeed")
			.msgBody("Login Succeed for application.")
			.build();

		try
		{
			// emailService.sendSimpleMail(emailDetails);
			log.info("###...success event is published...###" + success.getTimestamp());
		}
		catch (Exception e)
		{
			log.error("Error while sending onSuccess notification... " + e.getMessage());
		}
	}

	@EventListener
	public void onFailure(AbstractAuthenticationFailureEvent failures)
	{
		ProfileNotificationDetails emailDetails = ProfileNotificationDetails.builder()
			.recipient("ammydev321@gmail.com")
			.subject("Login Failure")
			.msgBody("Login Failed for application. Try with valid credentials.")
			.build();

		try
		{
			// emailService.sendSimpleMail(emailDetails);
			log.info("###...onFailure event is published...###" + failures.getTimestamp());
		}
		catch (Exception e)
		{
			log.error("Error while sending onFailure notification... " + e.getMessage());
		}
	}
}
