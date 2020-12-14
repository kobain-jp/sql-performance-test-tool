package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("oracle")
public class OracleStatsCollector implements StatsCollector {

	private JdbcTemplate jdbcTemplate;

	private static final String SQL = "SELECT SQL_FULLTEXT,EXECUTIONS,ELAPSED_TIME/EXECUTIONS,CPU_TIME/EXECUTIONS,BUFFER_GETS,DISK_READS,LAST_ACTIVE_TIME FROM V$SQLSTATS WHERE SQL_ID = ?";

	@Autowired
	public OracleStatsCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> collect(String sqlId) {

		Map<String, Object> statsMap = jdbcTemplate.queryForMap(SQL, sqlId);

		List<String> stats = new ArrayList<String>();
		stats.add(SQL.replace("?", sqlId));
		stats.add("");

		for (String key : statsMap.keySet()) {
			stats.add(key + ":" + statsMap.get(key));
		}

		return stats;
	}

}
