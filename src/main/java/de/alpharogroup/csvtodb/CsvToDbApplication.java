package de.alpharogroup.csvtodb;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class CsvToDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvToDbApplication.class, args);
    }

}
