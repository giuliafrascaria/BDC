package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	public String[][] galaxyInACircle(String[] inputs) throws Exception {
		int max = Integer.parseInt(inputs[0]);
		double range = Double.parseDouble(inputs[1]);
		String[] centreAsc = inputs[2].split("_");
		String[] centreDecl = inputs[3].split("_");
		double cRA = 15*((Double.parseDouble(centreAsc[0])) + (Double.parseDouble(centreAsc[1])/60) + (Double.parseDouble(centreAsc[2])/3600));
		int sgnDE;
		if (centreDecl[0].equals("+")){
			sgnDE = 1;
		} else {
			sgnDE = -1;
		}
		double cDE = sgnDE*((Double.parseDouble(centreDecl[1])) + (Double.parseDouble(centreDecl[2])/60) + (Double.parseDouble(centreDecl[3])/3600));
		PositionRepository pr = new PositionRepository();
		List<Position> allGal = pr.findAll();
		List<Double> distances = new ArrayList<Double>();
		List<Position> inCircle = new ArrayList<Position>();
		for (Position pos : allGal) {
			double ra1 = 15*((Double.parseDouble(pos.getRaH())) + (Double.parseDouble(pos.getRaM())/60) + (Double.parseDouble(pos.getRaS())/3600));
			int sgnDE1;
			if (pos.getDeSgn()){
				sgnDE1 = 1;
			} else {
				sgnDE1 = -1;
			}
			double de1 = sgnDE1*((Double.parseDouble(pos.getDeD())) + (Double.parseDouble(pos.getDeM())/60) + (Double.parseDouble(pos.getDeS())/3600));
			double res = Math.acos(Math.sin(cRA)*Math.sin(ra1) + Math.cos(cRA)*Math.cos(ra1)*Math.cos(cDE - de1));
			System.out.println(res);
			if (res < range) {
				distances.add(res);
				inCircle.add(pos);
			}
		}
		List<Double> sorted = distances;
		Collections.sort(sorted);
		int min= Math.min(max, sorted.size());
		String[] gal = new String[min];
		String[] dist = new String[min];
		for (int i=0; i<min; i++) {
			gal[i] = inCircle.get(distances.indexOf(sorted.get(i))).getGalaxy();
			dist[i] = String.valueOf(sorted.get(i));
		}
		String[][] result = {gal, dist};
		return result;
	}
}
