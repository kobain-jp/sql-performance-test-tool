package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@Profile("postgres")
public class PostgresExecutionPlanCollector implements ExecutionPlanCollector {

	private JdbcTemplate jdbcTemplate;

	private static final String SQL = "select plan from pg_store_plans where queryid_stat_statements =?";

	@Autowired
	public PostgresExecutionPlanCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> collect(String sqlId) {
		List<String> plans = new ArrayList<String>();
		plans.add(SQL.replace("?", sqlId));
		plans.add("");
		plans.addAll(jdbcTemplate.query(SQL, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("plan");
			}
		}, Long.parseLong(sqlId)));
		return plans;
	}
}
