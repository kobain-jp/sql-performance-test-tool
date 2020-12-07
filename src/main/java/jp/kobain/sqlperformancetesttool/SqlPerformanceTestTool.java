package jp.kobain.sqlperformancetesttool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.dbinstrument.DbInstrumentor;
import jp.kobain.sqlperformancetesttool.productinfo.ProductInfoCollector;
import jp.kobain.sqlperformancetesttool.sqlanalytics.SqlAnalyticsCollector;
import jp.kobain.sqlperformancetesttool.sqlexecute.SqlExecutor;
import jp.kobain.sqlperformancetesttool.sqlinstrument.SqlInstrumentor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SqlPerformanceTestTool {

	private final SqlInstrumentor sqlInstrumentor;
	private final DbInstrumentor dbInstrumentor;
	private final ProductInfoCollector productInfoCollector;
	private final SqlExecutor sqlExecutor;
	private final SqlAnalyticsCollector sqlAnalyticsCollector;

	@Autowired
	public SqlPerformanceTestTool(SqlInstrumentor sqlInstrumentor,DbInstrumentor dbInstrumentor, ProductInfoCollector productInfoCollector,
			SqlExecutor sqlExecutor, SqlAnalyticsCollector sqlAnalyticsCollector) {
		this.sqlInstrumentor = sqlInstrumentor;
		this.dbInstrumentor = dbInstrumentor;
		this.productInfoCollector = productInfoCollector;
		this.sqlExecutor = sqlExecutor;
		this.sqlAnalyticsCollector = sqlAnalyticsCollector;
	}

	public void run(int threadCount, int loopCount, long rampUpDurationMills, List<String> sqls, List<String> tableNames) {

		log.info("----------------------------");
		log.info("----------------------------");
		log.info("");
		log.info("Sql Performance Test Tool started");
		log.info("");
		log.info("----------------------------");
		log.info("----------------------------");

		try {

			productInfoCollector.collect().stream().forEach(e -> {
				log.info(e);
			});

			List<String> instrumentedSqls = sqlInstrumentor.instrument(sqls);

			if (tableNames.size() != 0) {
				dbInstrumentor.instrumentTables(tableNames);
			}

			sqlExecutor.execute(threadCount, loopCount, rampUpDurationMills, instrumentedSqls);

			sqlAnalyticsCollector.collect(instrumentedSqls).forEach(e -> {
				log.info(e);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
