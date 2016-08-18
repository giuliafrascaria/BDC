package controller;

import java.sql.SQLException;

import entity.Brightness;
import entity.Galaxy;
import entity.Position;
import exceptions.GalaxyNotExistsException;
import persistence.BrightnessRepository;
import persistence.GalaxyRepository;
import persistence.PositionRepository;

public class QueryController {
	private static QueryController instance = null;
	
	private QueryController() {
	}
	
    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return QueryController the current controller in the system
     */
	public synchronized static final QueryController getInstance() {
		 if (instance == null)
	            instance = new QueryController();
		 return instance;
	}
	
	public String[][] findGalaxy(String name) throws Exception {
		GalaxyRepository gr = new GalaxyRepository();
		Galaxy galaxy = gr.findByPrimaryKey(name);
		if (galaxy == null) {
			throw new GalaxyNotExistsException(name);
		}
		PositionRepository pr = new PositionRepository();
		Position position = pr.findByPrimaryKey(name);
		BrightnessRepository br = new BrightnessRepository();
		Brightness brightness1 = br.findByPrimaryKey(name, "NeV14.3");
		Brightness brightness2 = br.findByPrimaryKey(name, "NeV24.3");
		Brightness brightness3 = br.findByPrimaryKey(name, "OIV25.9");
		String sgn, fl1="", fl2="", fl3="";
		if (position.getDeSgn()) {
			sgn = "+";
		}else {
			sgn = "-";
		}
		if (brightness1.isFlag()) {
			fl1 = "<";
		}
		if (brightness2.isFlag()) {
			fl2 = "<";
		}
		if (brightness3.isFlag()) {
			fl3 = "<";
		}
		String[][] result ={ {galaxy.getName(), 
				position.getRaH(), position.getRaM(), position.getRaS(), sgn, position.getDeD(), position.getDeM(), position.getDeS(), 
				galaxy.getDistance(), position.getRedShift(), 
				fl1, brightness1.getVal(), fl2, brightness2.getVal(), fl3, brightness3.getVal(),
				galaxy.getMetalVal(), galaxy.getMetalErr()} };
		return result;
	}
	
	
	public String[][] findRedShift(String redshift) throws ClassNotFoundException, SQLException 
	{
		PositionRepository pr = new PositionRepository();
		Position position = pr.findByRedShift(redshift);
		
		String[][] result = {{position.getGalaxy(), position.getRedShift()}};
		
		return result;
	}
	
}


