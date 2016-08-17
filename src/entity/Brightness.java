package entity;

public class Brightness {
	private String ion;
	private boolean flag; //true if is an upper limit, otherwise false
	private String val;
	private String galaxy;
	
	public String getIon() {
		return ion;
	}
	
	public void setIon(String ion) {
		this.ion = ion;
	}
	
	public boolean isFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String getVal() {
		return val;
	}
	
	public void setVal(String val) {
		this.val = val;
	}

	public String getGalaxy() {
		return galaxy;
	}

	public void setGalaxy(String galaxy) {
		this.galaxy = galaxy;
	}
}
