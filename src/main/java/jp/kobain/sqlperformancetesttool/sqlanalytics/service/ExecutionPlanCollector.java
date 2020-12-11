package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.List;

public interface ExecutionPlanCollector {

	List<String> collect(String sqlId);

}