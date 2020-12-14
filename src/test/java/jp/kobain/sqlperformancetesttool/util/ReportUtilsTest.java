package jp.kobain.sqlperformancetesttool.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

class ReportUtilsTest {

	@Test
	void testAddFileSeparaterIfNeeded_HasSeparator() {
		assertEquals("./", ReportUtils.addFileSeparaterIfNeeded("./"));
	}

	void testAddFileSeparaterIfNeeded_NoSeparator() {
		assertEquals("." + File.separator, ReportUtils.addFileSeparaterIfNeeded("."));
	}

}
