package jp.kobain.sqlperformancetesttool.sqlinstrument;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.util.SqlUniqueKeyCommentUtils;

@Component
public class OracleSqIInstrumentor implements SqlInstrumentor {

	@Override
	public List<String> instrument(List<String> sqls) {

		return sqls.stream().map(sql -> {

			if (sql.endsWith(";")) {
				sql = sql.substring(0, sql.length() - 1);
			}
			return SqlUniqueKeyCommentUtils.appendUniqueKeyComment(sql);

		}).collect(Collectors.toList());
	}

}
