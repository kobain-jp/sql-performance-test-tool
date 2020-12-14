package jp.kobain.sqlperformancetesttool.databaseinfo;

import java.util.List;

public interface DatabaseInfoCollector {

	public List<String> collectProductInfo();
	
	public List<String> collectConfigInfo();
	
}
