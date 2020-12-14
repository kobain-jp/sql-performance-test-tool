package jp.kobain.sqlperformancetesttool.dbinstrument;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.dbinstrument.service.PostgresStatsResetor;
import jp.kobain.sqlperformancetesttool.dbinstrument.service.StatisticsUpdater;

@Component
@Profile("postgre")
public class PostgreDbInstrumentor implements DbInstrumentor {

	private final StatisticsUpdater statisticsUpdater;
	private final PostgresStatsResetor postgresStatsResetor;

	public PostgreDbInstrumentor(StatisticsUpdater statisticsUpdater, PostgresStatsResetor postgresStatsResetor) {
		this.statisticsUpdater = statisticsUpdater;
		this.postgresStatsResetor = postgresStatsResetor;
	}

	public void instrumentTables(List<String> tableNames) {

		tableNames.stream().forEach(tableName -> {

			if (!tableName.isEmpty() && !tableName.startsWith("#")) {
				statisticsUpdater.update(tableName.trim());
			}

		});

	}

	@Override
	public void instrumentStats() {
		postgresStatsResetor.reset();	
	};

}
