package jp.kobain.sqlperformancetesttool;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.util.ScriptUtils;

@Component
public class SqlPerformanceTestToolAppRunner implements CommandLineRunner {

	@Value("${sptt.thread-count}")
	private int threadCount;
	@Value("${sptt.loop-count}")
	private int loopCount;
	@Value("${sptt.rampup-duration-millsec}")
	private int rampUpDurationMills;
	@Value("${sptt.script-path}")
	private String scriptPath;

	private final SqlPerformanceTestTool sqlPerformanceTestTool;

	public SqlPerformanceTestToolAppRunner(SqlPerformanceTestTool sqlPerformanceTestTool) {
		this.sqlPerformanceTestTool = sqlPerformanceTestTool;
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			sqlPerformanceTestTool.run(this.threadCount, this.loopCount, this.rampUpDurationMills,
					ScriptUtils.readSqlScripts(this.scriptPath), ScriptUtils.readTableNames(this.scriptPath));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Bean("Thread")
	public Executor taskExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(this.threadCount);
		executor.setThreadNamePrefix("sptt-");
		executor.initialize();
		return executor;
	}

}
