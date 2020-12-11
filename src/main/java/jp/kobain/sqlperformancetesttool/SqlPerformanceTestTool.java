package jp.kobain.sqlperformancetesttool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.databaseinfo.DatabaseInfoCollector;
import jp.kobain.sqlperformancetesttool.dbinstrument.DbInstrumentor;
import jp.kobain.sqlperformancetesttool.report.ReportCreator;
import jp.kobain.sqlperformancetesttool.sqlanalytics.SqlAnalyticsCollector;
import jp.kobain.sqlperformancetesttool.sqlexecute.SqlExecutor;
import jp.kobain.sqlperformancetesttool.sqlexecute.model.ExecutionResult;
import jp.kobain.sqlperformancetesttool.sqlinstrument.SqlInstrumentor;
import jp.kobain.sqlperformancetesttool.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SqlPerformanceTestTool {

	private final SqlInstrumentor sqlInstrumentor;
	private final DbInstrumentor dbInstrumentor;
	private final DatabaseInfoCollector productInfoCollector;
	private final SqlExecutor sqlExecutor;
	private final SqlAnalyticsCollector sqlAnalyticsCollector;
	private final ReportCreator reportCreator;

	@Autowired
	public SqlPerformanceTestTool(SqlInstrumentor sqlInstrumentor, DbInstrumentor dbInstrumentor,
			DatabaseInfoCollector productInfoCollector, SqlExecutor sqlExecutor,
			SqlAnalyticsCollector sqlAnalyticsCollector, ReportCreator reportCreator) {
		this.sqlInstrumentor = sqlInstrumentor;
		this.dbInstrumentor = dbInstrumentor;
		this.productInfoCollector = productInfoCollector;
		this.sqlExecutor = sqlExecutor;
		this.sqlAnalyticsCollector = sqlAnalyticsCollector;
		this.reportCreator = reportCreator;
	}

	public void run(int threadCount, int loopCount, long rampUpDurationMills, List<String> sqls,
			List<String> tableNames, String outDir) {

		logStarted();

		try {

			// update statistics
			dbInstrumentor.instrumentTables(tableNames);
			// clear
			dbInstrumentor.instrumentStats();

			// add comment to fetch executed SQL
			List<String> instrumentedSqls = sqlInstrumentor.instrument(sqls);

			for (String instrumentedSql : instrumentedSqls) {

				// execute sql
				ExecutionResult result = sqlExecutor.executeSingleSql(threadCount, loopCount, rampUpDurationMills,
						instrumentedSql);

				// export report
				reportCreator.createReportAsFile(
						createReportResource(threadCount, loopCount, rampUpDurationMills, result, instrumentedSql), outDir);

			}

			logEnd();

		} catch (Exception e) {

			e.printStackTrace();
			logAbnormalEnd();
		}

	}

	private Map<String, Object> createReportResource(int threadCount, int loopCount, long rampUpDurationMills,
			ExecutionResult jvmExecutionResult, String instrumentedSql) {

		Map<String, Object> resource = new HashMap<String, Object>();
		resource.put("testDateTime", new SimpleDateFormat(ReportUtils.FORMAT_PATTERN).format(new Date()));
		resource.put("sql", instrumentedSql);
		resource.put("testParamThreadCount", threadCount);
		resource.put("testParamRampUpDurationMills", rampUpDurationMills);
		resource.put("testParamLoopCount", loopCount);
		resource.put("productInfo", productInfoCollector.collectProductInfo());
		resource.put("configInfo", productInfoCollector.collectConfigInfo());
		resource.put("jvmExecutionResult", jvmExecutionResult);
		resource.put("statistics", sqlAnalyticsCollector.collectStats(instrumentedSql));
		resource.put("executionPlan", sqlAnalyticsCollector.collectExecutionPlan(instrumentedSql));
		return resource;
	}

	private void logStarted() {
		log.info("----------------------------");
		log.info("");
		log.info("Sql Performance Test Tool started");
		log.info("");
		log.info("----------------------------");
	}

	private void logEnd() {
		log.info("----------------------------");
		log.info("");
		log.info("Sql Performance Test Tool end");
		log.info("");
		log.info("----------------------------");
	}

	private void logAbnormalEnd() {
		log.info("----------------------------");
		log.info("");
		log.info("Sql Performance Test Tool end with error");
		log.info("");
		log.info("----------------------------");
	}

}
