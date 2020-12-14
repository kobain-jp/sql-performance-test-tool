package jp.kobain.sqlperformancetesttool.report;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import freemarker.template.TemplateException;

public interface ReportCreator {

	public File  createReportAsFile(Map<String, Object> resource, String outDir)throws TemplateException, IOException,DataAccessException;

}
