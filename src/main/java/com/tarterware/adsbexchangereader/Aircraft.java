package com.tarterware.adsbexchangereader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft {
	@JsonProperty("Id")
	private Integer id;
	@JsonProperty("TSecs")
	private Long tSecs;
	@JsonProperty("Rcvr")
	private Integer rcvr;
	@JsonProperty("Icao")
	private String icao;
	@JsonProperty("Bad")
	private Boolean bad;
	@JsonProperty("Reg")
	private String reg;
	@JsonProperty("Alt")
	private Integer alt;
	@JsonProperty("GAlt")
	private Integer gAlt;
	@JsonProperty("Lat")
	private Float lat;
	@JsonProperty("Long")
	private Float lon;
	@JsonProperty("Spd")
	private Float spd;
	@JsonProperty("SpdTyp")
	private Integer spdTyp;
	@JsonProperty("Vsi")
	private Integer vsi;
	@JsonProperty("VsiT")
	private Integer vsiT;
	@JsonProperty("Trak")
	private Float trak;
	@JsonProperty("TrkH")
	private Boolean trkH;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("Mdl")
	private String mdl;
	@JsonProperty("Op")
	private String op;
	
	public Aircraft() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTSecs() {
		return tSecs;
	}

	public void setTSecs(Long tSecs) {
		this.tSecs = tSecs;
	}

	public Integer getRcvr() {
		return rcvr;
	}

	public void setRcvr(Integer rcvr) {
		this.rcvr = rcvr;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	public Boolean getBad() {
		return bad;
	}

	public void setBad(Boolean bad) {
		this.bad = bad;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public Integer getAlt() {
		return alt;
	}

	public void setAlt(Integer alt) {
		this.alt = alt;
	}

	public Integer getgAlt() {
		return gAlt;
	}

	public void setgAlt(Integer gAlt) {
		this.gAlt = gAlt;
	}

	public Float getLat() {
		return lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLon() {
		return lon;
	}

	public void setLon(Float lon) {
		this.lon = lon;
	}

	public Float getSpd() {
		return spd;
	}

	public void setSpd(Float spd) {
		this.spd = spd;
	}

	public Integer getSpdTyp() {
		return spdTyp;
	}

	public void setSpdTyp(Integer spdTyp) {
		this.spdTyp = spdTyp;
	}

	public Integer getVsi() {
		return vsi;
	}

	public void setVsi(Integer vsi) {
		this.vsi = vsi;
	}

	public Integer getVsiT() {
		return vsiT;
	}

	public void setVsiT(Integer vsiT) {
		this.vsiT = vsiT;
	}

	public Float getTrak() {
		return trak;
	}

	public void setTrak(Float trak) {
		this.trak = trak;
	}

	public Boolean getTrkH() {
		return trkH;
	}

	public void setTrkH(Boolean trkH) {
		this.trkH = trkH;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMdl() {
		return mdl;
	}
	
	public void setMdl(String mdl) {
		this.mdl = mdl;
	}
	
	public String getOp() {
		return op;
	}
	
	public void setOp(String op) {
		this.op = op;
	}
	
	@Override
	public String toString() {
		return "Aircraft {" +
				"Id=" + id + 
				", TSecs=" + tSecs + 
				", Rcvr=" + rcvr + 
				", Icao=" + icao + 
				", Bad=" + bad + 
				", Reg=" + reg + 
				", Alt=" + alt +
				", GAlt=" + gAlt +
				", Lat=" + lat +
				", Long=" + lon +
				", Spd=" + spd +
				", SpdTyp=" + spdTyp +
				", Vsi=" + vsi +
				", VsiT=" + vsiT +
				", Trak=" + trak +
				", TrkH=" + trkH +
				", Type=" + type +
				", Mdl=" + mdl +
				", Op=" + op +
				"}\n";
	}
	
}
