package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.kobain.sqlperformancetesttool.util.SqlUniqueKeyCommentUtils;

@Service
@Profile("postgres")
public class PostgresSqlIdResolver implements SqlIdResolver {

	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL = "SELECT QUERYID FROM pg_stat_statements WHERE QUERY like '%'||?||'%'";

	@Autowired
	public PostgresSqlIdResolver(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public String resolve(String sql) {

		String uniqueKeyComment = SqlUniqueKeyCommentUtils.extractUniqueKeyComment(sql);
		return jdbcTemplate.queryForObject(SQL, String.class, uniqueKeyComment);

	}

}
