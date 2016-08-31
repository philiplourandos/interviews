package com.allangray;

import com.allangray.batch.AGFieldSetMapper;
import com.allangray.batch.AGItemProcessor;
import com.allangray.batch.AGWriter;
import com.allangray.batch.OutputTasklet;
import com.allangray.biz.ICalc;
import com.allangray.biz.impl.VatCalc;
import com.allangray.model.Inventory;
import com.allangray.model.Product;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

/**
 *
 * @author lore
 */
@Configuration
@ComponentScan
@EnableBatchProcessing
@PropertySource("classpath:/batch-cfg.properties")
public class JobConfiguration {
    
    @Autowired
    private Environment env;
    
    @Bean
    @StepScope
    public FlatFileItemReader reader(@Value("#{jobParameters['input.file']}") Resource file) {
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
        tokenizer.setNames(new String[]{AGFieldSetMapper.ID, AGFieldSetMapper.DESC, AGFieldSetMapper.PRICE});

        DefaultLineMapper<Product> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new AGFieldSetMapper());

        reader.setLineMapper(mapper);
        reader.setResource(file);
        
        return reader;
    }

    @Bean
    public ItemWriter writer() {
        return new AGWriter();
    }
    
    @Bean
    public ItemProcessor processor() {
        return new AGItemProcessor();
    }
    
    @Bean(name = "items")
    public Map<String, Inventory> items() {
        return new TreeMap<String, Inventory>();
    }

    @Bean
    public ICalc vatCalc() {
        return new VatCalc();
    }
    
    @Bean(name = "msgs")
    public MessageSource msgBundle() {
        ResourceBundleMessageSource msgBundle = new ResourceBundleMessageSource();
        msgBundle.setBasename("com.allangray.batch.output");
        
        return msgBundle;
    }
    
    @Bean
    @Qualifier("readStep")
    public Step readFileStep(StepBuilderFactory stepBuilderFactory, 
            ItemReader reader, ItemWriter writer, ItemProcessor processor) {
        return stepBuilderFactory.get("readFileStep").<Product, Product> chunk(env.getProperty("batch.chunk.size", Integer.class))
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
    
    @Bean 
    @Qualifier("outputStep")
    public TaskletStep outputStep() {
        return new OutputTasklet();
    }

    @Bean
    public Job job(JobBuilderFactory jobFactory, @Qualifier("readStep") Step readStep, @Qualifier("outputStep")Step outputStep) {
        return jobFactory.get("agJob").incrementer(new RunIdIncrementer()).flow(readStep).next(outputStep).end().build();
    }
}
