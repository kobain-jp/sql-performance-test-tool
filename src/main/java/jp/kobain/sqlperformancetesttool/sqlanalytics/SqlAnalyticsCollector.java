package jp.kobain.sqlperformancetesttool.sqlanalytics;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.sqlanalytics.service.ExecutionPlanCollector;
import jp.kobain.sqlperformancetesttool.sqlanalytics.service.SqlIdResolver;
import jp.kobain.sqlperformancetesttool.sqlanalytics.service.StatsCollector;

@Component
public class SqlAnalyticsCollector {

	private final SqlIdResolver sqlIdResolver;
	private final StatsCollector statsCollector;
	private final ExecutionPlanCollector executionPlanCollector;

	@Autowired
	public SqlAnalyticsCollector(SqlIdResolver sqlIdResolver, StatsCollector statsCollector,
			ExecutionPlanCollector executionPlanCollector) {
		this.sqlIdResolver = sqlIdResolver;
		this.statsCollector = statsCollector;
		this.executionPlanCollector = executionPlanCollector;
	}

	public List<String> collectStats(String sql) {

		return statsCollector.collect(sqlIdResolver.resolve(sql));

	}


	public List<String> collectExecutionPlan(String sql) {

		return executionPlanCollector.collect(sqlIdResolver.resolve(sql));

	}

}
