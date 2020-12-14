package jp.kobain.sqlperformancetesttool.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import jp.kobain.sqlperformancetesttool.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MdReportCreator implements ReportCreator {

	String PATH = "jp/kobain/sqlperformancetesttool/report";
	String TEMPLATE_NAME = "report_template.md";

	private final Configuration cfg;
	private final Template template;

	public MdReportCreator() throws IOException {

		this.cfg = new Configuration(Configuration.VERSION_2_3_29);
		cfg.setDirectoryForTemplateLoading(new ClassPathResource(PATH).getFile());
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		cfg.setFallbackOnNullLoopVariable(false);

		this.template = cfg.getTemplate(TEMPLATE_NAME);

	}

	@Override
	public File createReportAsFile(Map<String, Object> data, String outDir)
			throws TemplateException, IOException, DataAccessException {
		String newFilePath = ReportUtils.createNewFilePath(outDir);
		File file = new File(newFilePath);
		template.process(data, new FileWriter(file));
		
		log.info(newFilePath + " is created");
		return file;

	}

}
