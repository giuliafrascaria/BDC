package entity;

public class PacsRow {
	
		
	private String ion;
	private String galaxy;
	private float val;
	private float err;
	private boolean flag;
	private String aperture;
	
	
	public String getIon()
	{
		return ion;
	}
	
	
	public void setIon(String ion) 
	{
		this.ion = ion;
	}
	
	
	public boolean isFlag() 
	{
		return flag;
	}
	
	
	public void setFlag(boolean flag) 
	{
		this.flag = flag;
	}
	
	
	public float getVal()
	{
		return val;
	}
	
	
	public void setVal(float val) 
	{
		this.val = val;
	}
	
	
	public float getErr() 
	{
		return err;
	}
	
	
	public void setErr(float err) 
	{
		this.err = err;
	}
	

	public String getGalaxy() 
	{
		return galaxy;
	}

	
	public void setGalaxy(String galaxy) 
	{
		this.galaxy = galaxy;
	}
	
	
	public String getAperture()
	{
		return aperture;
	}
	
	
	public void setAperture(String aperture)
	{
		this.aperture = aperture;
	}
	
}

 