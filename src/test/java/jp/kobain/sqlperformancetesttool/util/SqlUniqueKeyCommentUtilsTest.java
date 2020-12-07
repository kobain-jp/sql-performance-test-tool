package jp.kobain.sqlperformancetesttool.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SqlUniqueKeyCommentUtilsTest {

	@Test
	void testExtractUniqueKeyComment() {
		String uniqueKeyComment = SqlUniqueKeyCommentUtils
				.extractUniqueKeyComment("select * from usr/*f42d6fdd-36c6-4f24-b545-1ef2b4b2097b-169820854841981*/");

		assertEquals("/*f42d6fdd-36c6-4f24-b545-1ef2b4b2097b-169820854841981*/", uniqueKeyComment);

	}

}
