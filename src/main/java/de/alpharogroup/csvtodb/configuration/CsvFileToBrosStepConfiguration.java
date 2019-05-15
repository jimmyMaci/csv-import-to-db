package de.alpharogroup.csvtodb.configuration;

import javax.persistence.EntityManagerFactory;

import org.mapstruct.factory.Mappers;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import de.alpharogroup.csvtodb.entity.BrosEntity;
import de.alpharogroup.csvtodb.mapper.BrosEntityMapper;
import de.alpharogroup.migration.dto.BroDto;
import de.alpharogroup.reflection.ReflectionExtensions;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CsvFileToBrosStepConfiguration {

	EntityManagerFactory entityManagerFactory;

	ApplicationProperties applicationProperties;

	StepBuilderFactory stepBuilderFactory;

	PlatformTransactionManager transactionManager;

	@Bean
	public FileSystemResource brosResource() {
		String filePath = applicationProperties.getCsvDir() + "/"
				+ applicationProperties.getBrosFileName();
		return new FileSystemResource(filePath);
	}

	@Bean
	public FlatFileItemReader<BroDto> brosReader() {
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(";");
		delimitedLineTokenizer.setNames(ReflectionExtensions.getDeclaredFieldNames(BroDto.class));
		return SpringBatchObjectFactory.newFlatFileItemReader(brosResource(), delimitedLineTokenizer, BroDto.class);
	}

	@Bean
	public JpaItemWriter<BrosEntity> brosWriter() {
		JpaItemWriter<BrosEntity> newJpaItemWriter = SpringBatchObjectFactory.newJpaItemWriter(entityManagerFactory);
		return newJpaItemWriter;
	}

	@Bean
	public ItemProcessor<BroDto, BrosEntity> brosProcessor() {
		ItemProcessor<BroDto, BrosEntity> brosProcessor = new ItemProcessor<BroDto, BrosEntity>(){

			@Override
			public BrosEntity process(BroDto item) throws Exception {
				BrosEntity entity = Mappers.getMapper(BrosEntityMapper.class).mapToEntity(item);
				return entity;
			}
			
		};
		return brosProcessor;
	}

	@Bean
	public Step csvFileToBrosStep() {
		return stepBuilderFactory.get("csvFileToBrosStep").<BroDto, BrosEntity>chunk(10).reader(brosReader())
				.processor(brosProcessor())
				.writer(brosWriter())
				.transactionManager(transactionManager).build();
	}

}