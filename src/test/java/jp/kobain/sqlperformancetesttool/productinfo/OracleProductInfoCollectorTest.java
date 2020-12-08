package jp.kobain.sqlperformancetesttool.productinfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OracleProductInfoCollectorTest {

	PostgresProductInfoController it;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setup() {
		it = new PostgresProductInfoController(this.jdbcTemplate);
	}

	@Test
	void testCollect() {
		it.collect().stream().forEach(e -> System.out.println(e));
	}

}
