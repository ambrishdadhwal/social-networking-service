package com.social.network.config.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ProfileNotificationDetails
{

	private String recipient;
	private String msgBody;
	private String subject;
	private String attachment;
}
