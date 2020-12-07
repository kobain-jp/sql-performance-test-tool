package jp.kobain.sqlperformancetesttool.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SQLScriptUtilsTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLoadScript_FoundFiles() {

		try {
			List<String> sqls = ScriptUtils
					.readSqlScripts("./src/test/java/jp/kobain/sqlperformancetesttool/util");
			assertEquals("select * from dual;", sqls.get(0));
			assertEquals("select * from dual;", sqls.get(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testLoadScript_NotFound() {

		assertThrows(IOException.class, () -> {
			ScriptUtils.readSqlScripts("./sql1");
		});

	}

}
