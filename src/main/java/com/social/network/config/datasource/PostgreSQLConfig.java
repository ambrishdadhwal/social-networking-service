package com.social.network.config.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import lombok.AllArgsConstructor;

@Configuration
@EnableJdbcRepositories(jdbcOperationsRef = "postgreSQLJdbcOperations", basePackages = "com.social.network.repository")
@AllArgsConstructor
@Profile("!h2")
public class PostgreSQLConfig extends AbstractJdbcConfiguration
{

	private final Environment environment;

	@Bean("postgreDS")
	public DataSource postgreDS() throws IllegalAccessException
	{
		List<DataSourceProps> dbConns = Binder.get(environment).bind("social.database", Bindable.listOf(DataSourceProps.class))
			.orElseThrow(IllegalAccessException::new);
		DataSourceProps maria = dbConns.stream().filter(n -> n.getType().equals("POSTGRES")).findAny().orElse(null);
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(maria.getDriverClassName());
		dataSourceBuilder.url(maria.getUrl());
		dataSourceBuilder.username(maria.getUsername());
		dataSourceBuilder.password(maria.getPassword());
		return dataSourceBuilder.build();
	}

	@Bean("postgreSQLnamedJdbcOperations")
	NamedParameterJdbcOperations namedJdbcOperations(DataSource postgreDS)
	{
		return new NamedParameterJdbcTemplate(postgreDS);
	}

	@Bean("postgreSQLjdbcOperations")
	JdbcOperations jdbcOperations(DataSource postgreDS)
	{
		return new JdbcTemplate(postgreDS);
	}

}
