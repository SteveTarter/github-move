package com.tarterware.adsbexchangereader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CountriesController {

	private static final Logger log = LoggerFactory.getLogger(CountriesController.class);

	@GetMapping("/countries")
	public String countriesList(Model model) {
		CountriesModel countries = new CountriesModel() ;
		countries.setList(getCountries());
		model.addAttribute("countriesModel", countries);
		
		return "countries";
	}
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public List<String> getCountries() {
    	String sqlSelect = "SELECT DISTINCT cou FROM aircraft ORDER BY cou";
    	List<String> countries = jdbcTemplate.query( sqlSelect, new RowMapper<String>() { 
    	
    		public String mapRow(ResultSet result, int rowNum) throws SQLException {
    			
    			return result.getString("cou");
    		}
    	});
    	
    	log.info("Returning countries list with " + countries.size() + " entires.");
    	return countries ;
    }
    
}
