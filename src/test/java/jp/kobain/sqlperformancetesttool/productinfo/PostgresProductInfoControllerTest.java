package jp.kobain.sqlperformancetesttool.productinfo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import jp.kobain.sqlperformancetesttool.databaseinfo.PostgreProductInfoController;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostgresProductInfoControllerTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	PostgreProductInfoController it;

	@BeforeEach
	void setup() {
		it = new PostgreProductInfoController(this.jdbcTemplate);
	}

	@Test
	void testCollect() {
		try {
			it.collectConfigInfo().stream().forEach(e -> System.out.println(e));
			it.collectProductInfo().stream().forEach(e -> System.out.println(e));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
