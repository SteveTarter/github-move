package com.tarterware.adsbexchangereader;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private boolean skipFirst = true ;
    
    @Scheduled(fixedRate = 10000)
    public void checkFlightData() {
    	if(skipFirst) {
    		skipFirst = false;
    		return;
    	}
    	
    	String sqlSelect = "SELECT last_dv, total_ac FROM flight_data WHERE id = 1";
    	List<FlightData> listFlightData = jdbcTemplate.query( sqlSelect, new RowMapper<FlightData>() { 
    	
    		public FlightData mapRow(ResultSet result, int rowNum) throws SQLException {
    			FlightData bean = new FlightData();
    			
    			bean.setLastDv(result.getLong("last_dv"));
    			bean.setTotalAc(result.getInt("total_ac"));
    			return bean;
    		}
    	});
    	
    	FlightData data = listFlightData.get(0) ;
    	log.info(String.format("Flight data status: last_dv=%d, total_ac=%d", data.getLastDv(), data.getTotalAc()));
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
    	
    	log.info(String.format("%d aircraft found", flightData.getAcList().size()));
    	log.info(String.format("total_ac = %d", flightData.getTotalAc()));
    	String sqlUpdate = "UPDATE flight_data SET" +
    			" last_dv = " + flightData.getLastDv() +
    			",total_ac = " + flightData.getTotalAc() +
    			" WHERE id = 1;" ;
    	log.info("Updating table with \"" + sqlUpdate + "\"" );
    	jdbcTemplate.execute(sqlUpdate);
    	log.info("Updated table...");
    	//log.info(flightData.toString());
    	
    	ldv = flightData.getLastDv() ;
	}
}
