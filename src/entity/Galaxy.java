package entity;

public class Galaxy {
	private float distance;
	private float metalErr;
	private float metalVal;
	private String name;
	private String spectralClass;
	private String IRSmode;

	public float getDistance() {
		return distance;
	}
	
	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public float getMetalErr() {
		return metalErr;
	}
	
	public void setMetalErr(float metalErr) {
		this.metalErr = metalErr;
	}
	
	public float getMetalVal() {
		return metalVal;
	}
	
	public void setMetalVal(float metalVal) {
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
