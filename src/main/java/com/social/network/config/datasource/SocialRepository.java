package com.social.network.config.datasource;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.groovy.internal.util.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class SocialRepository
{

	@Autowired
	@Qualifier("postgresJdbcTemplate")
	protected JdbcTemplate postgresJdbcTemplate;

	public <T> CompletableFuture<List<T>> execute(String description, String query, Class<T> t) throws Exception
	{
		try
		{
			log.info("Query ::: {}", query);
			return timed(description, () -> postgresJdbcTemplate.query(query, new BeanPropertyRowMapper<>(t)));
		}
		catch (Exception e)
		{
			throw new Exception("::: Error fetching Metric ", e);
		}
	}

	public <T> CompletableFuture<List<T>> execute(String description, String query, List<Object> args, Class<T> t) throws Exception
	{
		try
		{
			log.info("Query ::: {}", query);
			return timed(description, () -> postgresJdbcTemplate.query(query, new BeanPropertyRowMapper<>(t), args.toArray()));
		}
		catch (Exception e)
		{
			throw new Exception("::: Error fetching Metric ", e);
		}
	}

	public <T> CompletableFuture<T> timed(String description, Supplier<T> code)
	{
		final long startTime = System.currentTimeMillis();
		T result = null;
		try
		{
			result = code.get();
		}
		catch (UncategorizedSQLException e)
		{
			log.error("UncategorizedSQLException :: ", e);
		}
		catch (Exception e)
		{
			log.error("Unknown Exception :: ", e);
		}
		finally
		{
			final Long duration = System.currentTimeMillis() - startTime;
			log.info("{} took {} ms", description, duration);
		}

		return CompletableFuture.completedFuture(result);
	}
}
