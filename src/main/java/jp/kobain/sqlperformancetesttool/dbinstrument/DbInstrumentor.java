package jp.kobain.sqlperformancetesttool.dbinstrument;

import java.util.List;

import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.dbinstrument.service.StatisticsUpdater;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DbInstrumentor {

	private final StatisticsUpdater statisticsUpdater;

	public DbInstrumentor(StatisticsUpdater statisticsUpdater) {
		this.statisticsUpdater = statisticsUpdater;
	}

	public void instrumentTables(List<String> tableNames) {
		log.info("----------------------------");
		log.info("----------------------------");
		log.info("Statistics update ");
		log.info("----------------------------");
		log.info("----------------------------");
		tableNames.stream().forEach(tableName -> {

			if (!tableName.isEmpty() && !tableName.startsWith("#")) {
				statisticsUpdater.update(tableName.trim());
				log.info("table:" + tableName + " updated");
			}
			
		});

	};

}
