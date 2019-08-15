package com.data.load.cosmoUser.config;

import com.data.load.cosmoUser.jobz.UserWriter;
import com.data.load.cosmoUser.models.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    @StepScope
    FlatFileItemReader<User> itemReader(@Value("#{jobParameters[file_path]}") String resource) {
        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        final FileSystemResource fileResource = new FileSystemResource(resource);
        itemReader.setResource(fileResource);
        itemReader.setLineMapper(new DefaultLineMapper<User>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"first_name", "last_name"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {
                    {
                        setTargetType(User.class);
                    }
                });
            }
        });
        return itemReader;
    }


    @Bean
    public UserWriter writer() {
        return new UserWriter();
    }

    @Bean
    public Step step1(FlatFileItemReader<User> reader, UserWriter writer) {
        return stepBuilderFactory.get("step1")
                .<User, User>chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ReadUserJobListener readUserJobListener() {
        return new ReadUserJobListener();
    }

    @Bean
    public Job readUsersJob(Step step0, Step step1) {
        return jobBuilderFactory.get("readUsersJob")
                .incrementer(new RunIdIncrementer())
                .listener(readUserJobListener())
                .flow(step1)
                .end()
                .build();
    }
}
