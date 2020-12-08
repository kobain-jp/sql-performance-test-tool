package jp.kobain.sqlperformancetesttool.productinfo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgresProductInfoControllerTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	PostgresProductInfoController it;

	@BeforeEach
	void setup() {
		it = new PostgresProductInfoController(this.jdbcTemplate);
	}

	@Test
	void testCollect() {
		try {
			it.collect().stream().forEach(e -> System.out.println(e));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
