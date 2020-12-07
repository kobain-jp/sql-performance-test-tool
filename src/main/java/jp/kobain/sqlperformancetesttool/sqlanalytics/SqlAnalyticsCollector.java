package jp.kobain.sqlperformancetesttool.sqlanalytics;

import java.util.List;

public interface SqlAnalyticsCollector {

	List<String> collect(List<String> sqls);

}