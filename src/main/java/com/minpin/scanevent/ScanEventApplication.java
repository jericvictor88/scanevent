package com.minpin.scanevent;

import com.minpin.scanevent.model.CartonEvt;
import com.minpin.scanevent.services.CartonEvtFieldSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
@EnableBatchProcessing
@Slf4j
public class ScanEventApplication {
	public static String[] tokens = new String[] {"Label","Courier","Status","Date"};

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<CartonEvt> scanEventReader() {
		FlatFileItemReader<CartonEvt> itemReader = new FlatFileItemReader<>();
		itemReader.setLinesToSkip(1);
		itemReader.setResource(new ClassPathResource("scan_event.csv"));

		DefaultLineMapper<CartonEvt> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(tokens);

		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new CartonEvtFieldSetMapper());

		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	@Bean
	public Step scanEventStep() {
		return this.stepBuilderFactory.get("scanEventStep")
				.<CartonEvt, CartonEvt>chunk(3)
				.reader(scanEventReader())
				.writer(items -> {
					log.info("Received scan events of size {}", items.size());
					items.forEach(System.out::println);
				})
				.build();
	}

	@Bean
	public Job scanEventJob() {
		return this.jobBuilderFactory.get("scanEventJob").start(scanEventStep()).build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ScanEventApplication.class, args);
	}

}
