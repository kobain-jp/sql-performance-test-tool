package jp.kobain.sqlperformancetesttool.sqlexecute;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.kobain.sqlperformancetesttool.sqlexecute.service.AsyncSqlExecuter;
import jp.kobain.sqlperformancetesttool.util.MapUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SqlExecutor {

	private final AsyncSqlExecuter asyncSqlExecuter;

	@Autowired
	public SqlExecutor(AsyncSqlExecuter asyncSqlExecuter) {

		this.asyncSqlExecuter = asyncSqlExecuter;

	}

	public void execute(int threadCount, int loopCount, long rampUpDurationMills, List<String> sqls)
			throws InterruptedException, DataAccessException, ExecutionException {

		log.info("----------------------------");
		log.info("----------------------------");
		log.info("SqlExecutor start");
		log.info("----------------------------");

		log.info("----------------------------");
		log.info("Test Parameter Information");
		log.info("----------------------------");
		log.info("ThreadCount:" + threadCount);
		log.info("RampUp Duratin(ms):" + rampUpDurationMills);
		log.info("LoopCount:" + loopCount);
		log.info("----------------------------");
		log.info("Execute SQL List");
		log.info("----------------------------");
		sqls.stream().forEach(e -> {
			log.info(e);
		});
		log.info("----------------------------");
		log.info("SQL Execution");
		log.info("----------------------------");
		Map<String, Long> totalExecutionTimePerSql = new LinkedHashMap<String, Long>();
		for (int i = 0; i < loopCount; i++) {

			// run Threads
			List<CompletableFuture<Map<String, Long>>> completableFutureList = runThreads(threadCount,
					rampUpDurationMills, sqls);

			// Next Loop after all thread finished
			CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[completableFutureList.size()]))
					.join();

			// Sum up execution time per sql
			for (CompletableFuture<Map<String, Long>> cf : completableFutureList) {
				totalExecutionTimePerSql = MapUtils.mergeAndSummingLongGroupByKey(totalExecutionTimePerSql, cf.get());
			}

		}

		log.info("----------------------------");
		log.info("Execution Result");
		log.info("----------------------------");
		for (String sql : sqls) {
			log.info("SQL:" + sql);
			log.info("Execution:" + loopCount * threadCount);
			log.info("EXECUTION_TIME/EXECUTIONS:" + totalExecutionTimePerSql.get(sql) / loopCount);
			log.info("");
		}

	}

	private List<CompletableFuture<Map<String, Long>>> runThreads(int threadCount, long rampUpDurationMills,
			List<String> sqls) throws InterruptedException {

		long rampUpIntervalMills = calcRampUpIntervalMills(threadCount, rampUpDurationMills);

		List<CompletableFuture<Map<String, Long>>> completableFutureList = new ArrayList<CompletableFuture<Map<String, Long>>>();
		for (int i = 0; i < threadCount; i++) {

			completableFutureList.add(asyncSqlExecuter.execute(1, sqls));
			Thread.sleep(rampUpIntervalMills);

		}
		return completableFutureList;
	}

	private long calcRampUpIntervalMills(int threadCount, long rampUpDurationSec) {
		return rampUpDurationSec / threadCount;
	}

}
