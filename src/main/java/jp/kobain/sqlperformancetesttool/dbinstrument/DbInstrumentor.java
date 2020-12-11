package jp.kobain.sqlperformancetesttool.dbinstrument;

import java.util.List;

public interface DbInstrumentor {

	public void instrumentTables(List<String> tableNames);

	public void instrumentStats();

}