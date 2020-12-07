package jp.kobain.sqlperformancetesttool;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.kobain.sqlperformancetesttool.util.ScriptUtils;

@SpringBootTest
class SqlPerformanceTestToolTest {

	@Autowired
	SqlPerformanceTestTool it;

	@Test
	void test() {
		try {
			int threadCount = 20;
			long rampUpDurationMills = 60; //
			int loopCount = 5;
			List<String> sqls = ScriptUtils.readSqlScripts("./testscript");
			List<String> tables = ScriptUtils.readTableNames("./testscript");

			it.run(threadCount, loopCount, rampUpDurationMills, sqls, tables);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
