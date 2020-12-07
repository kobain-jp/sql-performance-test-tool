package jp.kobain.sqlperformancetesttool.sqlanalytics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.sqlanalytics.service.ExecutionPlanCollector;
import jp.kobain.sqlperformancetesttool.sqlanalytics.service.SqlIdResolver;
import jp.kobain.sqlperformancetesttool.sqlanalytics.service.StatsCollector;

@Component
public class OracleSqlAnalyticsCollector implements SqlAnalyticsCollector {

	private final SqlIdResolver sqlIdResolver;
	private final StatsCollector statsCollector;
	private final ExecutionPlanCollector executionPlanCollector;

	@Autowired
	public OracleSqlAnalyticsCollector(SqlIdResolver sqlIdResolver, StatsCollector statsCollector,
			ExecutionPlanCollector executionPlanCollector) {
		this.sqlIdResolver = sqlIdResolver;
		this.statsCollector = statsCollector;
		this.executionPlanCollector = executionPlanCollector;
	}

	@Override
	public List<String> collect(List<String> sqls) {

		List<String> Analytics = new ArrayList<String>();
		Analytics.add("----------------------------");
		Analytics.add("----------------------------");
		Analytics.add("Sql Analytics start");

		
		sqls.stream().forEach(sql -> {
			String sqlId = sqlIdResolver.resolve(sql);
			Analytics.add("----------------------------");
			Analytics.add("----------------------------");
			Analytics.add("Analysis target SQL:" + sql);
			Analytics.add("----------------------------");
			Analytics.add("----------------------------");
			Analytics.add("V$SQLSTATS");
			Analytics.add("----------------------------");
			Analytics.addAll(statsCollector.collect(sqlId));
			Analytics.add("----------------------------");
			Analytics.add("Execution Plan");
			Analytics.add("----------------------------");
			Analytics.addAll(executionPlanCollector.collect(sqlId));

		});

		return Analytics;

	}

}
