package com.tarterware.adsbexchangereader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ScheduledTasks {

	private long ldv ;

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	
    public static String QUERY_URL="https://public-api.adsbexchange.com/VirtualRadar/AircraftList.json" ;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
    	RestTemplate restTemplate = builder.build();
    	return restTemplate;
    }
    
    @Scheduled(fixedRate = 20000)
    public void retrieveFlightData() {
    	HttpHeaders headers = new HttpHeaders();
    	List<MediaType> mediaTypeList = new ArrayList<MediaType>() ;
    	mediaTypeList.add(MediaType.APPLICATION_JSON);
    	headers.setAccept(mediaTypeList);
    	headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
    	HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

    	String requestUrl = QUERY_URL ;
    	if( ldv != 0 ) {
    		requestUrl = requestUrl + "?ldv=" + ldv ;
    	}
    	
    	log.info(String.format("requestUrl=\"%s\"", requestUrl));
    	FlightData flightData = restTemplate.exchange(requestUrl, HttpMethod.GET,entity,FlightData.class).getBody();
    	log.info(flightData.toString());
    	log.info(String.format("%d aircraft found", flightData.getAcList().size()));
    	
    	ldv = flightData.getLastDv() ;
	}
}
