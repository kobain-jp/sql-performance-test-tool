package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SqlIdResolverTest {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	SqlIdResolver it;
	
	@BeforeEach
	void setUp() throws Exception {
		it = new SqlIdResolver(jdbcTemplate);
	}

	@Test
	void test() {
		String sqlId =it.resolve("select * from usr /*0a7053f9-b226-4254-a3d7-7e2d4a2b3b2a-170809228862537*/");
		System.out.println(sqlId);
	}

}
