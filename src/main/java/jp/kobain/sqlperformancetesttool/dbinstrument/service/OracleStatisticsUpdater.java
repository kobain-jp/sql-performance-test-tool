package jp.kobain.sqlperformancetesttool.dbinstrument.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OracleStatisticsUpdater implements StatisticsUpdater {

	private JdbcTemplate jdbcTemplate;
	private final String SQL1 = "ANALYZE TABLE ";
	private final String SQL2 = " COMPUTE STATISTICS";

	@Autowired
	public OracleStatisticsUpdater(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void update(String tableName) {
		// can not use bind value because it leads grammar error by quoto
		jdbcTemplate.update(SQL1 + tableName + SQL2);
	}

}
