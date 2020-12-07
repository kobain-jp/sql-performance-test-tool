package jp.kobain.sqlperformancetesttool.dbinstrument;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.kobain.sqlperformancetesttool.dbinstrument.service.StatisticsUpdater;

@SpringBootTest
class StatisticsUpdaterTest {

	@Autowired
	StatisticsUpdater it;

	@Test
	void test() {

		it.update("usr");
		
		
	}

}
