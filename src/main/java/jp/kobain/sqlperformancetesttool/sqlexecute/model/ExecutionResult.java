package jp.kobain.sqlperformancetesttool.sqlexecute.model;

public class ExecutionResult {

	private final String sql;
	private final int executionCount;
	private final long averageExecutionTime;

	public ExecutionResult(String sql, int executionCount, long averageExecutionTime) {
		this.sql = sql;
		this.executionCount = executionCount;
		this.averageExecutionTime = averageExecutionTime;
	}

	public String getSql() {
		return sql;
	}

	public int getExecutionCount() {
		return executionCount;
	}

	public long getAverageExecutionTime() {
		return averageExecutionTime;
	}

}
