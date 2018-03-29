package com.tarterware.adsbexchangereader;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightData {

	private Long lastDv;
	private Integer totalAc;
	private Integer src;
	private Long shtTrlSec;
	private Long stm;
	@JsonProperty("acList")
	private List<Aircraft> acList = new ArrayList<Aircraft>();
	private Integer srcFeed;
	private Boolean configChanged;
	
	public FlightData() {
	}

	public Long getLastDv() {
		return lastDv;
	}

	public void setLastDv(Long lastDv) {
		this.lastDv = lastDv;
	}

	public Integer getTotalAc() {
		return totalAc;
	}

	public void setTotalAc(Integer totalAc) {
		this.totalAc = totalAc;
	}

	public Integer getSrc() {
		return src;
	}

	public void setSrc(Integer src) {
		this.src = src;
	}

	
	public Long getShtTrlSec() {
		return shtTrlSec;
	}

	public void setShtTrlSec(Long shtTrlSec) {
		this.shtTrlSec = shtTrlSec;
	}

	public Long getStm() {
		return stm;
	}

	public void setStm(Long stm) {
		this.stm = stm;
	}

	public List<Aircraft> getAcList() {
		return acList;
	}
	
	public void setAcList(List<Aircraft> acList) {
		this.acList = acList ;
	}
	
	public Integer getSrcFeed() {
		return srcFeed;
	}

	public void setSrcFeed(Integer srcFeed) {
		this.srcFeed = srcFeed;
	}

	public Boolean getConfigChanged() {
		return configChanged;
	}

	public void setConfigChanged(Boolean configChanged) {
		this.configChanged = configChanged;
	}

	@Override
	public String toString() {
		return "FlightData {" +
				"lastDv=" + lastDv + 
				", totalAc=" + totalAc + 
				", src=" + src + 
				", shtTrlSec=" + shtTrlSec +
				", stm=" + stm +
				", acList=" + acList +
				", srcFeed=" + srcFeed +
				", configChanged=" + configChanged +
				"}";
	}
	
}
