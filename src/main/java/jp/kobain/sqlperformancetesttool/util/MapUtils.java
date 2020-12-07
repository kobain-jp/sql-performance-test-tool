package jp.kobain.sqlperformancetesttool.util;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapUtils {

	public static Map<String, Long> mergeAndSummingLongGroupByKey(Map<String, Long> m1, Map<String, Long> m2) {

		return Stream.concat(m1.entrySet().stream(), m2.entrySet().stream())
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingLong(Map.Entry::getValue)));

	}

}
