package de.alpharogroup.csvtodb.configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import de.alpharogroup.spring.batch.factory.SpringBatchObjectFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import de.alpharogroup.csvtodb.entity.FriendsEntity;
import de.alpharogroup.csvtodb.mapper.FriendsEntityMapper;
import de.alpharogroup.migration.dto.FriendDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CsvFileToFriendsStepConfiguration {

	EntityManagerFactory entityManagerFactory;

	ApplicationProperties applicationProperties;

	StepBuilderFactory stepBuilderFactory;

	PlatformTransactionManager transactionManager;

	@Bean
	public FileSystemResource friendsResource() {
		String filePath = applicationProperties.getCsvDir() + "/" + applicationProperties.getFriendsFileName();
		return new FileSystemResource(filePath);
	}

	@Bean
	public FlatFileItemReader<FriendDto> friendsReader() {
		return SpringBatchObjectFactory
			.newCsvFileItemReader(friendsResource(), FriendDto.class, ";", 1);
	}

	@Bean
	public JpaItemWriter<FriendsEntity> friendsWriter() {
		return SpringBatchObjectFactory.newJpaItemWriter(entityManagerFactory);
	}

	@Bean
	public ItemProcessor<FriendDto, FriendsEntity> friendsProcessor() {

		ItemProcessor<FriendDto, FriendsEntity> processor = new ItemProcessor<FriendDto, FriendsEntity>() {

			@Override
			public FriendsEntity process(FriendDto item) throws Exception {
				FriendsEntity entity = Mappers.getMapper(FriendsEntityMapper.class).mapToEntity(item);
				return entity;
			}

		};
		return processor;
	}

	@Bean
	public Step csvFileToFriendsStep() {
		return stepBuilderFactory.get("csvFileToFriendsStep").<FriendDto, FriendsEntity>chunk(10)
				.reader(friendsReader()).processor(friendsProcessor()).writer(friendsWriter()).faultTolerant()
				.skip(FlatFileParseException.class).skip(PersistenceException.class).skipLimit(10)
				.allowStartIfComplete(true).transactionManager(transactionManager).build();
	}

}