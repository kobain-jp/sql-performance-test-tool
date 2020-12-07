package jp.kobain.sqlperformancetesttool.sqlanalytics;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OracleSqlAnalyticsCollectorTest {
	
	@Autowired
	SqlAnalyticsCollector it;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		List<String> sqls = new ArrayList<String>();
		sqls.add("select * from usr /*0a7053f9-b226-4254-a3d7-7e2d4a2b3b2a-170809228862537*/");
		it.collect(sqls);
	}

}
