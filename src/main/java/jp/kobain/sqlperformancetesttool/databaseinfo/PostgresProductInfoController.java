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
@Profile("postgres")
public class PostgresProductInfoController implements DatabaseInfoCollector {

	private JdbcTemplate jdbcTemplate;
	private static String SQL_PRODUCT_INFO = "SELECT version();";
	private static String SQL_CONFIG_INFO = "SELECT name, setting FROM  pg_settings  where name in ('max_connections','shared_buffers','effective_cache_size','work_mem,maintenance_work_mem','checkpoint_segments','checkpoint_completion_target','fsync,synchronous_commit','random_page_cost','effective_io_concurrency') ";

	@Autowired
	public PostgresProductInfoController(JdbcTemplate jdbcTemplate) {
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
				return rs.getString(1);
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
				return rs.getString("name") + ":" + rs.getString("setting");
			};
		}));
		
		return configInfo;
	}

}
