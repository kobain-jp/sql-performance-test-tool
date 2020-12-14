package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OracleStatsCollectorTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	StatsCollector it;

	@BeforeEach
	void setUp() throws Exception {
		it = new OracleStatsCollector(jdbcTemplate);
	}

	@Test
	void test() {
		List<String> stats = it.collect("14bhcqk4vwrf6");
		stats.stream().forEach(e -> System.out.println(e));
	}

}
