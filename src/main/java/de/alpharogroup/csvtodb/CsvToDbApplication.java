package de.alpharogroup.csvtodb;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import de.alpharogroup.csvtodb.configuration.ApplicationProperties;

@EnableBatchProcessing
@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class })
@SpringBootApplication
public class CsvToDbApplication extends SpringBootServletInitializer
{

	public static void main(String[] args) {
		SpringApplication.run(CsvToDbApplication.class, args);
	}

}
