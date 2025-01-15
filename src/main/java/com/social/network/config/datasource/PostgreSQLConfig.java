package com.social.network.config.datasource;

import java.util.List;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import lombok.AllArgsConstructor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages =
		{"com.social.network.entity.postgres"})
@EnableJpaRepositories(
		basePackages = "com.social.network.repository.postgres",
		entityManagerFactoryRef = "postgresqlEntityManagerFactory",
		transactionManagerRef = "postgresqlTransactionManager"
)
@Profile("!h2")
@RequiredArgsConstructor
public class PostgreSQLConfig extends AbstractJdbcConfiguration
{
	private final Environment environment;

	@Bean("postgresqlDataSource")
	@LiquibaseDataSource
	public DataSource postgreDS() throws IllegalAccessException
	{
		List<DataSourceProps> dbConns = Binder.get(environment).bind("social.database", Bindable.listOf(DataSourceProps.class))
			.orElseThrow(IllegalAccessException::new);
		DataSourceProps postgres = dbConns.stream().filter(n -> n.getType().equals("POSTGRES")).findAny().orElse(null);
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(postgres.getDriverClassName());
		dataSourceBuilder.url(postgres.getUrl());
		dataSourceBuilder.username(postgres.getUsername());
		dataSourceBuilder.password(postgres.getPassword());
		return dataSourceBuilder.build();
	}

	@Bean(name = "postgresqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("postgresqlDataSource") DataSource dataSource) {
		return builder
				.dataSource(dataSource)
				.packages("com.social.network.entity.postgres")
				.persistenceUnit("postgresql")
				.build();
	}

	@Bean(name = "postgresqlTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean(name = "primaryLiquibaseProperties")
	@ConfigurationProperties("liquibase-changelogs.primary.liquibase")
	public LiquibaseProperties primaryLiquibaseProperties() {
		return new LiquibaseProperties();
	}

	@Bean(name = "liquibase")
	public SpringLiquibase primaryLiquibase(@Qualifier("postgresqlDataSource") DataSource dataSource,
											@Qualifier("primaryLiquibaseProperties") LiquibaseProperties liquibaseProperties) throws IllegalAccessException {
		SpringLiquibase primary = new SpringLiquibase();
		primary.setDataSource(dataSource);
		primary.setChangeLog(liquibaseProperties.getChangeLog());

		return primary;
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean("postgresJdbcTemplate")
	@Profile("!h2")
	JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresqlDataSource") DataSource dataH2Source)
	{
		return new JdbcTemplate(dataH2Source);
	}
}
