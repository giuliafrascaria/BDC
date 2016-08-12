package entity;

public class Galaxy {
	private String distance;
	private String metalErr;
	private String metalVal;
	private String name;
	private String spectralClass;
	private String IRSmode;

	public String getDistance() {
		return distance;
	}
	
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public String getMetalErr() {
		return metalErr;
	}
	
	public void setMetalErr(String metalErr) {
		this.metalErr = metalErr;
	}
	
	public String getMetalVal() {
		return metalVal;
	}
	
	public void setMetalVal(String metalVal) {
		this.metalVal = metalVal;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSpectralClass() {
		return spectralClass;
	}
	
	public void setSpectralClass(String spectralClass) {
		this.spectralClass = spectralClass;
	}
	
	public String getIRSmode() {
		return IRSmode;
	}
	
	public void setIRSmode(String iRSmode) {
		IRSmode = iRSmode;
	}
}
