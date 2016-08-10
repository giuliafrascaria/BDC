package entity;

public class Position {
	private float raH;
	private float raM;
	private float raS;
	private boolean deSgn;//true if +, false if -
	private float deD;
	private float deM;
	private float deS;
	private float redShift;
	private String galaxy;
	
	public float getRaH() {
		return raH;
	}
	
	public void setRaH(float raH) {
		this.raH = raH;
	}
	
	public float getRaM() {
		return raM;
	}
	
	public void setRaM(float raM) {
		this.raM = raM;
	}
	
	public float getRaS() {
		return raS;
	}
	
	public void setRaS(float raS) {
		this.raS = raS;
	}
	
	public boolean getDeSgn() {
		return deSgn;
	}
	
	public void setDeSgn(boolean desgn) {
		this.deSgn = desgn;
	}
	
	public float getDeD() {
		return deD;
	}
	
	public void setDeD(float deD) {
		this.deD = deD;
	}
	
	public float getDeM() {
		return deM;
	}
	
	public void setDeM(float deM) {
		this.deM = deM;
	}
	
	public float getDeS() {
		return deS;
	}
	
	public void setDeS(float deS) {
		this.deS = deS;
	}
	
	public float getRedShift() {
		return redShift;
	}
	
	public void setRedShift(float redShift) {
		this.redShift = redShift;
	}

	public String getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(String galaxy) {
		this.galaxy = galaxy;
	}
	
	
}
