package de.alpharogroup.csvtodb.configuration;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;

@Component
@AllArgsConstructor
@Log
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CsvToDatabaseJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("afterJob");		
	}

}
