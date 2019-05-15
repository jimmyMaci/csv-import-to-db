package de.alpharogroup.csvtodb.configuration;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SpringBatchObjectFactory {

    private static final String READER_SUFFIX = "Reader";

    public static <T> FlatFileItemReader<T> newFlatFileItemReader(Resource resource, DelimitedLineTokenizer delimitedLineTokenizer, Class<T> typeClass) {
    	DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
    	BeanWrapperFieldSetMapper<T> fieldSetMapper = new BeanWrapperFieldSetMapper<T>();
    	fieldSetMapper.setTargetType(typeClass);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        return new FlatFileItemReaderBuilder<T>()
                .name(typeClass.getSimpleName() + READER_SUFFIX)
                .resource(resource)
                .lineMapper(lineMapper)
                .linesToSkip(1)
                .build();
    }

    public static <T> JpaItemWriter<T> newJpaItemWriter(EntityManagerFactory entityManagerFactory){
        JpaItemWriter<T> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }


    public static <T> T getTargetEntity(EntityManager entityManager, Class<T> entityClass, Supplier<T> newEntitySupplier) {
        return newEntitySupplier.get();
    }

}
