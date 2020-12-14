package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("postgres")
public class PostgresStatsCollector implements StatsCollector {

	private JdbcTemplate jdbcTemplate;

	private static final String SQL = "SELECT query,rows,calls,total_time,total_time/calls as average_time FROM pg_stat_statements WHERE queryid = ?";

	@Autowired
	public PostgresStatsCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> collect(String sqlId) {

		Map<String, Object> statsMap = jdbcTemplate.queryForMap(SQL, Long.parseLong(sqlId));

		List<String> stats = new ArrayList<String>();
		stats.add(SQL.replace("?", sqlId));
		stats.add("");

		for (String key : statsMap.keySet()) {
			stats.add(key + ":" + statsMap.get(key));
		}

		return stats;
	}

}
