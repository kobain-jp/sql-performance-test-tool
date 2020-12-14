package jp.kobain.sqlperformancetesttool.databaseinfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Profile("oracle")
public class OracleProductInfoCollector implements DatabaseInfoCollector {

	private JdbcTemplate jdbcTemplate;
	private static String SQL_PRODUCT_INFO = "select * from v$version";
	private static String SQL_CONFIG_INFO = "select name, value from V$SYSTEM_PARAMETER where name = 'processes' or name='sessions'";

	@Autowired
	public OracleProductInfoCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<String> collectProductInfo() {
		List<String> productInfo = new ArrayList<String>();
		productInfo.add(SQL_PRODUCT_INFO);
		productInfo.add("");
		productInfo.addAll(jdbcTemplate.query(SQL_PRODUCT_INFO, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
				return rs.getString("BANNER");
			};
		}));
		return productInfo;
	}

	@Override
	public List<String> collectConfigInfo() {
		List<String> configInfo = new ArrayList<String>();
		configInfo.add(SQL_CONFIG_INFO);
		configInfo.add("");
		configInfo.addAll(jdbcTemplate.query(SQL_CONFIG_INFO, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
				return rs.getString("name") + ":" + rs.getString("VALUE");
			};
		}));
		return configInfo;
	}

}
