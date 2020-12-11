package jp.kobain.sqlperformancetesttool.sqlexecute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jp.kobain.sqlperformancetesttool.sqlexecute.model.ExecutionResult;
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

	public ExecutionResult executeSingleSql(int threadCount, int loopCount, long rampUpDurationMills, String sql)
			throws InterruptedException, DataAccessException, ExecutionException {

		return executeSqls(threadCount, loopCount, rampUpDurationMills, Arrays.asList(new String[] { sql })).get(sql);

	}

	public LinkedHashMap<String, ExecutionResult> executeSqls(int threadCount, int loopCount, long rampUpDurationMills,
			List<String> sqls) throws InterruptedException, DataAccessException, ExecutionException {

		logInfoTestParameters(threadCount, loopCount, rampUpDurationMills);

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

		return createExecutionResults(threadCount, loopCount, sqls, totalExecutionTimePerSql);

	}

	private LinkedHashMap<String, ExecutionResult> createExecutionResults(int threadCount, int loopCount,
			List<String> sqls, Map<String, Long> totalExecutionTimePerSql) {

		LinkedHashMap<String, ExecutionResult> results = new LinkedHashMap<String, ExecutionResult>();

		for (String sql : sqls) {
			int executionCount = loopCount * threadCount;
			long averageExecutionTime = totalExecutionTimePerSql.get(sql) / loopCount;
			results.put(sql, new ExecutionResult(sql, loopCount * threadCount, averageExecutionTime));

			logExecutionResults(sql, executionCount, averageExecutionTime);

		}
		return results;
	}

	private void logExecutionResults(String sql, int executionCount, long averageExecutionTime) {
		log.info("SqlExecutor - SQL:" + sql);
		log.info("SqlExecutor - Execution:" + executionCount);
		log.info("SqlExecutor - EXECUTION_TIME/EXECUTIONS:" + averageExecutionTime);
	}

	private void logInfoTestParameters(int threadCount, int loopCount, long rampUpDurationMills) {
		log.info("----------------------------");
		log.info("Test Parameter ");
		log.info("----------------------------");
		log.info("ThreadCount:" + threadCount);
		log.info("RampUp Duratin(ms):" + rampUpDurationMills);
		log.info("LoopCount:" + loopCount);
	}

	private List<CompletableFuture<Map<String, Long>>> runThreads(int threadCount, long rampUpDurationMills,
			List<String> sqls) throws InterruptedException {

		long rampUpIntervalMills = calcRampUpIntervalMills(threadCount, rampUpDurationMills);

		List<CompletableFuture<Map<String, Long>>> completableFutureList = new ArrayList<CompletableFuture<Map<String, Long>>>();
		for (int i = 0; i < threadCount; i++) {

			completableFutureList.add(asyncSqlExecuter.executeSqls(1, sqls));
			Thread.sleep(rampUpIntervalMills);

		}
		return completableFutureList;
	}

	private long calcRampUpIntervalMills(int threadCount, long rampUpDurationSec) {
		return rampUpDurationSec / threadCount;
	}

}
