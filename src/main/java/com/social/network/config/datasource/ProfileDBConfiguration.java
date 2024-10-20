package com.social.network.config.datasource;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration(proxyBeanMethods = false)
@Slf4j
@RequiredArgsConstructor
public class ProfileDBConfiguration
{

	private final Environment env;

	private final DataSourceProps dataSource;

	@Bean
	@Profile("dev")
	@Primary
	public DataSource dataDevSource()
	{
		log.info("###...Dev Profile is active...###");
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(dataSource.getDriverClassName());
		dataSourceBuilder.url(dataSource.getUrl());
		dataSourceBuilder.username(dataSource.getUsername());
		dataSourceBuilder.password(dataSource.getPassword());
		return dataSourceBuilder.build();
	}

	@Bean
	@Profile("prod")
	@Primary
	public DataSource dataProdSource()
	{
		log.info("###...prod Profile is active...###");
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(dataSource.getDriverClassName());
		dataSourceBuilder.url(dataSource.getUrl());
		dataSourceBuilder.username(dataSource.getUsername());
		dataSourceBuilder.password(dataSource.getPassword());
		return dataSourceBuilder.build();
	}

	@Bean
	@Profile("h2")
	@Primary
	public DataSource dataH2Source()
	{
		log.info("###...h2 Profile is active...###");
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		//dataSourceBuilder.driverClassName(dataSource.getDriverClassName());
		dataSourceBuilder.url(dataSource.getUrl());
		dataSourceBuilder.username(dataSource.getUsername());
		dataSourceBuilder.password(dataSource.getPassword());
		return dataSourceBuilder.build();
	}

	@ConditionalOnMissingBean(name = "postgresJdbcTemplate")
	@Bean("postgresJdbcTemplate")
	@Profile("h2")
	JdbcTemplate jdbcTemplate(DataSource dataH2Source)
	{
		return new JdbcTemplate(dataH2Source);
	}
}
