package jp.kobain.sqlperformancetesttool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SqlPerformanceTestToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlPerformanceTestToolApplication.class, args).close();
	}

}
