package com.social.network.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
public class DataSourceProps
{

	private String identifier;
	private String type;
	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private Integer connections;
	private Integer minimumIdle;
	private String connectionTimeOut;
	private String isEnabled;

	enum Type
	{
		POSTGRES,
		MONGO,
		DRUID,
		MYSQL,
		MSSQL,
		INMEMORY,
		MARIA
	}
}
