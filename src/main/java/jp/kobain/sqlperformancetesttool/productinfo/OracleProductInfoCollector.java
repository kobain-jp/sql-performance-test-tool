package jp.kobain.sqlperformancetesttool.productinfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class OracleProductInfoCollector implements ProductInfoCollector {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public OracleProductInfoCollector(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> collect() {
		List<String> productInfo = new ArrayList<String>();
		productInfo.add("----------------------------");
		productInfo.add("----------------------------");
		productInfo.add("Product info ");
		productInfo.add("----------------------------");
		productInfo.add("----------------------------");
		productInfo.addAll(jdbcTemplate.query("select * from v$version", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
				return rs.getString("BANNER");
			};
		}));

		productInfo.addAll(jdbcTemplate.query(
				"select name, value from V$SYSTEM_PARAMETER where name = 'processes' or name='sessions'",
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
						return rs.getString("name") + ":" + rs.getString("VALUE");
					};
				}));
		
		return productInfo;

	}

}
