package jp.kobain.sqlperformancetesttool.sqlinstrument;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jp.kobain.sqlperformancetesttool.util.SqlUniqueKeyCommentUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SqIInstrumentorImpl implements SqlInstrumentor {

	@Override
	public List<String> instrument(List<String> sqls) {

		return sqls.stream().map(sql -> {

			String instrumentedSql = sql;
			if (sql.endsWith(";")) {
				instrumentedSql = sql.substring(0, sql.length() - 1);
			}
			instrumentedSql = SqlUniqueKeyCommentUtils.appendUniqueKeyComment(instrumentedSql);
			log.info("instrumented:" + instrumentedSql);
			return instrumentedSql;

		}).collect(Collectors.toList());
	}

}
