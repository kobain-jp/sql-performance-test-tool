package jp.kobain.sqlperformancetesttool.productinfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@Profile("postgres")
public class PostgresProductInfoController implements ProductInfoCollector {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public PostgresProductInfoController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> collect() {
		List<String> productInfo = new ArrayList<String>();
		productInfo.add("----------------------------");
		productInfo.add("----------------------------");
		productInfo.add("Product info ");
		productInfo.add("----------------------------");
		productInfo.add("----------------------------");
		productInfo.addAll(jdbcTemplate.query("SELECT version();", new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
				return rs.getString(1);
			};
		}));
		
		productInfo.addAll(jdbcTemplate.query(
				"SELECT name, setting FROM  pg_settings  where name ='max_connections' ",
				new RowMapper<String>() {
					@Override
					public String mapRow(ResultSet rs, int rowNum) throws java.sql.SQLException {
						return rs.getString("name") + ":" + rs.getString("setting");
					};
				}));
		
		return productInfo;

	}

}
