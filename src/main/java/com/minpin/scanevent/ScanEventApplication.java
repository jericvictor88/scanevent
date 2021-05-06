package com.minpin.scanevent;

import com.minpin.scanevent.model.CartonEvt;
import com.minpin.scanevent.model.CartonEvtExt;
import com.minpin.scanevent.services.CartonEvtExtProcessor;
import com.minpin.scanevent.services.CartonEvtFieldSetMapper;
import com.minpin.scanevent.services.CartonEvtPreparedStatemeSetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.util.Date;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@Slf4j
public class ScanEventApplication {
	private static final String INSERT_CARTONEVT_SQL = "insert into `mydb`.`cartonEvtExt` (label, courier, status, date, isFinalEvent) values (?, ?, ?, ?, ?)";
	private static String[] tokens = new String[] {"Label","Courier","Status","Date"};

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	@Autowired
	public DataSource dataSource;
	@Autowired
	public JobLauncher jobLauncher;

	@Scheduled(cron = "0/30 * * * * *")
	public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		JobParametersBuilder parameterBuilder = new JobParametersBuilder();
		parameterBuilder.addDate("runTime", new Date());
		this.jobLauncher.run(scanEventJob(), parameterBuilder.toJobParameters());
	}

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
	public ItemProcessor<CartonEvt, CartonEvtExt> scanEventProcessor() {
		return new CartonEvtExtProcessor();
	}

	@Bean
	public ItemWriter<CartonEvtExt> scanEventWriter() {
		return new JdbcBatchItemWriterBuilder<CartonEvtExt>()
				.dataSource(dataSource)
				.sql(INSERT_CARTONEVT_SQL)
				.itemPreparedStatementSetter(new CartonEvtPreparedStatemeSetter())
				.build();
	}

	@Bean
	public Step scanEventStep() {
		return this.stepBuilderFactory.get("scanEventStep")
				.<CartonEvt, CartonEvtExt>chunk(3)
				.reader(scanEventReader())
				.processor(scanEventProcessor())
				.writer(scanEventWriter())
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
