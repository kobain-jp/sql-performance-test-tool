package jp.kobain.sqlperformancetesttool.sqlanalytics.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatsCollectorTest {

	@Autowired
	StatsCollector it;

	@Test
	void test() {
		List<String> stats = it.collect("14bhcqk4vwrf6");
		stats.stream().forEach(e -> System.out.println(e));
	}

}
