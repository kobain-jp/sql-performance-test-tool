package jp.kobain.sqlperformancetesttool.dbinstrument.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("postgres")
@Slf4j
public class PostgresStatisticsUpdater implements StatisticsUpdater {

	private JdbcTemplate jdbcTemplate;
	private final String SQL = "ANALYZE";

	@Autowired
	public PostgresStatisticsUpdater(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void update(String tableName) {
		jdbcTemplate.update(SQL);
		log.info(SQL + " executed");
	}

}
