package com.social.network.config.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ProfileNotificationServiceImpl implements ProfileNotificationService
{

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	String userName;

	@Override
	public boolean sendSimpleMail(ProfileNotificationDetails details)
	{
		try
		{
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(userName);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			javaMailSender.send(mailMessage);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendMailWithAttachment(ProfileNotificationDetails details)
	{
		return false;
	}

}
