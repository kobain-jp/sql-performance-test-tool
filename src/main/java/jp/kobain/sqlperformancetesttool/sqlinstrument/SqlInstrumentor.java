package jp.kobain.sqlperformancetesttool.sqlinstrument;

import java.util.List;

public interface SqlInstrumentor {

	List<String> instrument(List<String> sqls);

}