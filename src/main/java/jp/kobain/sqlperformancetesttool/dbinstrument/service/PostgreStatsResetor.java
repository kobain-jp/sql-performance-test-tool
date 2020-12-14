package jp.kobain.sqlperformancetesttool.dbinstrument.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("postgre")
@Slf4j
public class PostgreStatsResetor {

	private JdbcTemplate jdbcTemplate;
	private final String SQL = "SELECT pg_stat_statements_reset();";

	@Autowired
	public PostgreStatsResetor(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void reset() {
		jdbcTemplate.queryForList(SQL);
		log.info(SQL + "is executed");

	}

}
