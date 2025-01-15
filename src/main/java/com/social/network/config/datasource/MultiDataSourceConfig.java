/*package com.social.network.config.datasource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Profile("!h2")
public class MultiDataSourceConfig
{

	final static String jdbcTemplate = "JdbcTemplate";
	final static String nameParameterJdbcTemplate = "NamedParameterJdbcTemplate";
	final static String dataSourceName = "DataSource";

	@Bean
	public BeanDefinitionRegistrar beanDefinitionRegistrar(Environment environment)
	{
		return new BeanDefinitionRegistrar(environment);
	}

	public static class BeanDefinitionRegistrar implements BeanDefinitionRegistryPostProcessor
	{

		private Environment environment;

		public BeanDefinitionRegistrar(Environment environment)
		{
			this.environment = environment;
		}

		@Override
		public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
		{
			try
			{
				List<DataSourceProps> dbConns = Binder.get(environment).bind("social.database", Bindable.listOf(DataSourceProps.class))
					.orElseThrow(IllegalAccessException::new);
				for (DataSourceProps sourceProps : dbConns)
				{
					if(sourceProps.getIsEnabled().equals("true"))
					{
						DataSource dataSource = dataSource(sourceProps);
						GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
						beanDefinition.setBeanClass(DataSource.class);
						beanDefinition.setInstanceSupplier(() -> dataSource);
						registry.registerBeanDefinition(sourceProps.getIdentifier() + dataSourceName, beanDefinition);
						registry.registerBeanDefinition(sourceProps.getIdentifier() + jdbcTemplate, instantiate(JdbcTemplate.class, dataSource));
						registry.registerBeanDefinition(sourceProps.getIdentifier() + nameParameterJdbcTemplate, instantiate(NamedParameterJdbcTemplate.class, dataSource));

					}
				}
			}
			catch (IllegalAccessException e)
			{
				log.error("Error while creating Datasource bean - {}", e.getLocalizedMessage());
				throw new RuntimeException(e);
			}
		}

		private GenericBeanDefinition instantiate(Class<?> target, DataSource dataSource)
		{
			GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
			beanDefinition.setBeanClass(target);
			beanDefinition.setInstanceSupplier(() -> {
				try
				{
					return target.getDeclaredConstructor(DataSource.class).newInstance(dataSource);
				}
				catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
				{
					log.error("Error while creating Datasource bean instantiate - {}", e.getLocalizedMessage());
				}
				return null;
			});

			return beanDefinition;
		}

		private DataSource dataSource(DataSourceProps dbConn)
		{
			HikariConfig config = new HikariConfig();
			config.setMaximumPoolSize(dbConn.getConnections());
			config.setMinimumIdle(dbConn.getMinimumIdle());
			config.setMaxLifetime(50000);
			switch (DataSourceProps.Type.valueOf(dbConn.getType()))
			{
				case POSTGRES:
				{
					config.setDriverClassName(dbConn.getDriverClassName());
					config.setJdbcUrl(dbConn.getUrl());
					config.setUsername(dbConn.getUsername());
					config.setPassword(dbConn.getPassword());
					break;
				}
				default:
					break;
			}
			return new HikariDataSource(config);
		}

	}
}
*/