package com.tarterware.adsbexchangereader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class Application 
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static void main( String[] args )
    {
    	SpringApplication.run(Application.class);
    }
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
    	RestTemplate restTemplate = builder.build();
    	return restTemplate;
    }
    
    /*
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception
    {
    	return args -> {
        	HttpHeaders headers = new HttpHeaders();
        	List<MediaType> mediaTypeList = new ArrayList<MediaType>() ;
        	mediaTypeList.add(MediaType.APPLICATION_JSON);
        	headers.setAccept(mediaTypeList);
        	headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        	FlightData flightData = restTemplate.exchange("https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json", HttpMethod.GET,entity,FlightData.class).getBody();
    		
        	log.info(flightData.toString());
    	};
    }
    */
}
