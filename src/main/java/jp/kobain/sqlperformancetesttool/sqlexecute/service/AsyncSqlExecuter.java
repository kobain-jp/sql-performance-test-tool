package jp.kobain.sqlperformancetesttool.sqlexecute.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncSqlExecuter {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public AsyncSqlExecuter(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Async("Thread")
	public CompletableFuture<Map<String, Long>> executeSqls(int callCount, List<String> sqls)
			throws InterruptedException {

		Map<String, Long> totalExecutionTimePerSql = new LinkedHashMap<String, Long>();

		IntStream.range(0, callCount).forEach(index -> {

			sqls.stream().forEach(sql -> {

				long elapsedTimeMicrosec = doExecute(sql);
				totalExecutionTimePerSql.put(sql, totalExecutionTimePerSql.getOrDefault(sql, 0L) + elapsedTimeMicrosec);

			});

		});

		return CompletableFuture.completedFuture(totalExecutionTimePerSql);

	}

	@Async("Thread")
	public CompletableFuture<Map<String, Long>> executeSingleSql(int callCount, String sql)
			throws InterruptedException {

		return executeSqls(callCount, Arrays.asList(new String[] { sql }));

	}

	private long doExecute(String sql) {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		stopWatch.stop();
		long elapsedTimeMicrosec = stopWatch.getTotalTimeNanos() / 1000;
		log.debug("sql:" + sql + " Execution Time:" + elapsedTimeMicrosec + " Rows:" + list.size());

		return elapsedTimeMicrosec;
	}

}
