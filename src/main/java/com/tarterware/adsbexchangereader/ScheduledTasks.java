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
    
    /*
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
    	
    	sqlSelect = "SELECT DISTINCT cou FROM aircraft ORDER BY cou";
    	List<String> countries = jdbcTemplate.query( sqlSelect, new RowMapper<String>() { 
    	
    		public String mapRow(ResultSet result, int rowNum) throws SQLException {
    			
    			return result.getString("cou");
    		}
    	});
    	log.info(String.format("%d countries found", countries.size()));
    	for(int n = 0; n < countries.size();  ++n) {
    		log.info(countries.get(n));
    	}
    }
    */
    
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
    	
    	for(int n = 0 ;  n < flightData.getAcList().size();  ++n)
    	{
    		updateAircraft( flightData.getAcList().get(n)) ;
    	}
    	ldv = flightData.getLastDv() ;
	}
    
    public void updateAircraft(Aircraft aircraft) {
    	// Determine if aircraft DB record exists for this icao
    	String sqlSelect = "SELECT alt FROM aircraft WHERE icao = '" + aircraft.getIcao() + "'";
    	List<Aircraft> listAircraft = jdbcTemplate.query( sqlSelect, new RowMapper<Aircraft>() { 
        
    		public Aircraft mapRow(ResultSet result, int rowNum) throws SQLException {
    			Aircraft bean = new Aircraft();
    			
    			bean.setAlt(result.getInt("alt"));
    			return bean;
    		}
    	});

    	// Doesn't exist - insert into table
    	if(listAircraft.isEmpty()) {
    		String sqlInsert = "INSERT INTO aircraft(icao,cou,lat,lon,alt) VALUES(?,?,?,?,?)";
    		Object[] values = new Object[5];
    		values[0] = aircraft.getIcao();
    		values[1] = aircraft.getCou();
    		values[2] = aircraft.getLat();
    		values[3] = aircraft.getLon();
    		values[4] = aircraft.getAlt();
    		int[] types = new int[5];
    		types[0] = java.sql.Types.VARCHAR;
    		types[1] = java.sql.Types.VARCHAR;
    		types[2] = java.sql.Types.FLOAT;
    		types[3] = java.sql.Types.FLOAT;
    		types[4] = java.sql.Types.FLOAT;
    		jdbcTemplate.update(sqlInsert,values,types);
    	}
    	// Existed - update table
    	else {
    		String sqlUpdate = "UPDATE aircraft SET cou = ?, lat = ?, lon = ?, alt = ? WHERE icao = ?;";
    		Object[] values = new Object[5];
    		values[0] = aircraft.getCou();
    		values[1] = aircraft.getLat();
    		values[2] = aircraft.getLon();
    		values[3] = aircraft.getAlt();
    		values[4] = aircraft.getIcao();
    		int[] types = new int[5];
    		types[0] = java.sql.Types.VARCHAR;
    		types[1] = java.sql.Types.FLOAT;
    		types[2] = java.sql.Types.FLOAT;
    		types[3] = java.sql.Types.FLOAT;
    		types[4] = java.sql.Types.VARCHAR;
    		jdbcTemplate.update(sqlUpdate,values,types);
    	}
    }
}
