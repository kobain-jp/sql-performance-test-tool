package jp.kobain.sqlperformancetesttool.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportUtils {

	private static final String PREFIX = "sptt-";
	private static final String EXTENSION_MD = ".md";
	public static final String FORMAT_PATTERN = "yyyy-MM-dd-HH:mm:ss";

	public static String addFileSeparaterIfNeeded(String parent) {
		if (!parent.endsWith(File.separator)) {
			parent = parent + File.separator;
		}
		return parent;
	}

	public static String createFileNameWithTimestamp() {
		return PREFIX + new SimpleDateFormat(FORMAT_PATTERN).format(new Date()) + EXTENSION_MD;
	}

	public static String createNewFilePath(String parent) {
		return addFileSeparaterIfNeeded(parent) + createFileNameWithTimestamp();
	}

}
