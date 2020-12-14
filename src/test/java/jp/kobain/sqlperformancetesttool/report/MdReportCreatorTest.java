package jp.kobain.sqlperformancetesttool.report;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import freemarker.template.TemplateException;
import jp.kobain.sqlperformancetesttool.databaseinfo.DatabaseInfoCollector;
import jp.kobain.sqlperformancetesttool.databaseinfo.PostgreProductInfoController;
import jp.kobain.sqlperformancetesttool.sqlanalytics.SqlAnalyticsCollector;
import jp.kobain.sqlperformancetesttool.sqlexecute.model.ExecutionResult;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MdReportCreatorTest {

	@Autowired
	JdbcTemplate jdbcTemplate;

	MdReportCreator it;
	DatabaseInfoCollector productInfoCollector;
	SqlAnalyticsCollector sqlAnalyticsCollector;

	@BeforeEach
	void setUp() throws Exception {
		it = new MdReportCreator();
		productInfoCollector = new PostgreProductInfoController(jdbcTemplate);
	}

	@Test
	void testCreateReportAsFile() {

		try {
			Map<String, Object> data = new HashMap<String, Object>();
			String sql = "select * from book";

			List<String> mockData = new ArrayList<String>();
			mockData.add("line1");
			mockData.add("line2");
			mockData.add("line3");
			data.put("testDateTime", new Date().toString());
			data.put("sql", sql);
			data.put("testParamThreadCount", 100);
			data.put("testParamRampUpDurationMills", 6000);
			data.put("testParamLoopCount", 5);

			data.put("productInfo", mockData);
			data.put("configInfo", mockData);
			data.put("jvmExecutionResult", new ExecutionResult(sql, 1, 2L));
			data.put("statistics", mockData);
			data.put("executionPlan", mockData);

			it.createReportAsFile(data, ".");
		} catch (DataAccessException | TemplateException | IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
