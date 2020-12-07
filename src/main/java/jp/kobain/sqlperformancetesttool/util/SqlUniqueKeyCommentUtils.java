package jp.kobain.sqlperformancetesttool.util;

import java.util.UUID;

public class SqlUniqueKeyCommentUtils {

	private SqlUniqueKeyCommentUtils() {

	}

	private static String generateUniqueKeyComment() {
		return " /*" + UUID.randomUUID() + "-" + System.nanoTime() + "*/";
	}

	public static String appendUniqueKeyComment(String sql) {
		return sql + generateUniqueKeyComment();
	}

	public static String extractUniqueKeyComment(String sql) {
		return sql.substring(sql.lastIndexOf("/*"));
	}
}
