package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.List;

public interface StatsCollector {

	List<String> collect(String sqlId);

}