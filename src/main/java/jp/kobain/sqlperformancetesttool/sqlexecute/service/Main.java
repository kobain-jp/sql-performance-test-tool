package jp.kobain.sqlperformancetesttool.sqlexecute.service;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("a", map.getOrDefault("a", (long) 0) + 1);
	}
}
