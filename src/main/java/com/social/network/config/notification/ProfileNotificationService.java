package com.social.network.config.notification;

public interface ProfileNotificationService
{
	public boolean sendSimpleMail(ProfileNotificationDetails details);

	public boolean sendMailWithAttachment(ProfileNotificationDetails details);
}
