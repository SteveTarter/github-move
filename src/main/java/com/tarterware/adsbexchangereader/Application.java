package com.tarterware.adsbexchangereader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);
    
	public static void main(String[] args) {
    	SpringApplication.run(Application.class);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
    	RestTemplate restTemplate = builder.build();
    	return restTemplate;
    }
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public void run(String... strings) throws Exception {
    	log.info("Creating tables");
    	
    	jdbcTemplate.execute("DROP TABLE flight_data IF EXISTS");
    	jdbcTemplate.execute("DROP TABLE aircraft IF EXISTS");
    	jdbcTemplate.execute("CREATE TABLE flight_data(" +
    			"id INTEGER , " +
    			"last_dv NUMBER, " +
    			"total_ac NUMBER)");
    	jdbcTemplate.execute("CREATE TABLE aircraft(" +
    			"icao VARCHAR(6), " +
    			"cou VARCHAR(100), " +
    			"lat NUMBER, " +
    			"lon NUMBER, " +
    			"alt NUMBER)");
    	jdbcTemplate.execute("INSERT INTO flight_data(id,last_dv,total_ac) VALUES (1,0,0)");
    }
}
