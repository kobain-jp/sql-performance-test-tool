package jp.kobain.sqlperformancetesttool.dbinstrument.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgreStatisticsUpdaterTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	PostgresStatisticsUpdater it;

	@BeforeEach
	void setup() {
		it = new PostgresStatisticsUpdater(this.jdbcTemplate);
	}

	@Test
	void testUpdate() {
		try {
			it.update("");
		} catch (Exception e) {
			fail();
		}
		
	}

}
