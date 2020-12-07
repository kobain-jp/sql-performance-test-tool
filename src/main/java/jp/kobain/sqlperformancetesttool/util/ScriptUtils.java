package jp.kobain.sqlperformancetesttool.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScriptUtils {

	private ScriptUtils() {
	}

	public static List<String> readSqlScripts(String path) throws IOException {

		File dir = new File(path);
		List<String> sqls = new ArrayList<String>();

		for (File file : dir.listFiles()) {
			
			if (file.getName().toLowerCase().endsWith(".sql")) {
				String sql = String.join(" ", Files.readAllLines((Paths.get(file.getAbsolutePath()))));
				sqls.add(sql);
			}
		}

		return sqls;

	}

	public static List<String> readTableNames(String path) throws IOException {

		File dir = new File(path);
		List<String> sqls = new ArrayList<String>();

		for (File file : dir.listFiles()) {
			file.getName().toLowerCase().contentEquals("instrument_target_tbls.txt");
			return Files.readAllLines((Paths.get(file.getAbsolutePath())));

		}

		return sqls;

	}

}
