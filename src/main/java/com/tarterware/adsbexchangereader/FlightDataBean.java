package com.tarterware.adsbexchangereader;

public class FlightDataBean {
	private long lastDv ;
	private int totalAc ;
	public long getLastDv() {
		return lastDv;
	}
	public void setLastDv(long lastDv) {
		this.lastDv = lastDv;
	}
	public int getTotalAc() {
		return totalAc;
	}
	public void setTotalAc(int totalAc) {
		this.totalAc = totalAc;
	}
	@Override
	public String toString() {
		return "FlightDataBean [lastDv=" + lastDv + ", totalAc=" + totalAc + "]";
	}	
}
