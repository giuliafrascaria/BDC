package entity;

public class PacsContinuousRow {
	private String ion;
 	private String galaxy;
 	private String val;
 	private String err;
 	private boolean flag;
 	private String aperture;
	
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
 	
 	public String getErr() {
 		return err;
 	}
 	
 	public void setErr(String err) {
 		this.err = err;
 	}
	 	
 	public String getGalaxy() {
 		return galaxy;
	}
	
 	public void setGalaxy(String galaxy) {
 		this.galaxy = galaxy;
 	}
	 	
	public String getAperture() {
		return aperture;
 	}
	 	
	public void setAperture(String aperture) {
		this.aperture = aperture;
 	}
}
