package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entity.Brightness;
import entity.Galaxy;
import entity.PacsContinuousRow;
import entity.PacsRow;
import entity.Position;
import entity.SpitzerRow;
import exceptions.ClassNotExistsException;
import exceptions.FluxNotExistsException;
import exceptions.GalaxyNotExistsException;
import persistence.BrightnessRepository;
import persistence.GalaxyRepository;
import persistence.PacsContRepository;
import persistence.PacsRowRepository;
import persistence.PositionRepository;
import persistence.SpitzerRowRepository;

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
		Brightness brightness1 = br.findByPrimaryKey(name, "nev14.3");
		Brightness brightness2 = br.findByPrimaryKey(name, "nev24.3");
		Brightness brightness3 = br.findByPrimaryKey(name, "oiv25.9");
		String sgn, fl1="", fl2="", fl3="";
		if (position.getDeSgn()) {
			sgn = "+";
		}else {
			sgn = "-";
		}
		if (brightness1 == null) {
			brightness1 = new Brightness();
			fl1 = "/";
			brightness1.setVal("/");
		} else if (brightness1.isFlag()) {
			fl1 = "<";
		}
		if (brightness2 == null) {
			brightness2 = new Brightness();
			fl2 = "/";
			brightness2.setVal("/");
		} else if (brightness2.isFlag()) {
			fl2 = "<";
		}
		if (brightness3 == null) {
			brightness3 = new Brightness();
			fl3 = "/";
			brightness3.setVal("/");
		} else if (brightness3.isFlag()) {
			fl3 = "<";
		}
		String[][] result ={ {galaxy.getName(), 
				position.getRaH(), position.getRaM(), position.getRaS(), sgn, position.getDeD(), position.getDeM(), position.getDeS(), 
				galaxy.getDistance(), position.getRedShift(), 
				fl1, brightness1.getVal(), fl2, brightness2.getVal(), fl3, brightness3.getVal(),
				galaxy.getMetalVal(), galaxy.getMetalErr()} };
		return result;
	}
	

	
	public String[][] findRedShift(String[] inputs) throws Exception 
	{
		PositionRepository pr = new PositionRepository();
		
		List<Position> allRes = pr.findByRedShift(inputs);
		List<String> galaxy = new ArrayList<String>();
		List<String> redshift = new ArrayList<String>();
		for(Position pos : allRes)
		{
			galaxy.add(pos.getGalaxy());
			redshift.add(pos.getRedShift());	
		}
		
		int size = Integer.parseInt(inputs[2]);
		String[] gal = new String[size];
		String[] rs = new String[size];
		for(int i = 0; i < size; i++)
		{
			gal[i] = galaxy.get(i);
			rs[i] = String.valueOf(redshift.get(i));
		}
		String[][] result = {gal, rs};
		
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

	public String[][] fluxRatio(String galaxy, String fluxNum, String fluxDen, String aperN, String aperD) throws Exception{
		GalaxyRepository gr = new GalaxyRepository();
		if (gr.findByPrimaryKey(galaxy) == null) {
			throw new GalaxyNotExistsException(galaxy);
		}
		SpitzerRowRepository spr = new SpitzerRowRepository();
		PacsRowRepository prr = new PacsRowRepository();
		float num, den;
		boolean flagN, flagD;
		if (spr.findByPrimaryKey(galaxy, fluxNum)==null && prr.findByPrimaryKey(galaxy, fluxNum, aperN)==null){
			throw new FluxNotExistsException(galaxy, 1);
		}
		if (spr.findByPrimaryKey(galaxy, fluxDen)==null && prr.findByPrimaryKey(galaxy, fluxDen, aperD)==null){
			throw new FluxNotExistsException(galaxy, 2);
		}
		if (spr.findByPrimaryKey(galaxy, fluxNum)!=null) {
			SpitzerRow sr = spr.findByPrimaryKey(galaxy, fluxNum);
			num = Float.parseFloat(sr.getVal());
			flagN = sr.isFlag();
		} else {
			PacsRow pr = prr.findByPrimaryKey(galaxy, fluxNum, aperN);
			num = Float.parseFloat(pr.getVal());
			flagN = pr.isFlag();
		}
		if (spr.findByPrimaryKey(galaxy, fluxDen)!=null) {
			SpitzerRow sr = spr.findByPrimaryKey(galaxy, fluxDen);
			den = Float.parseFloat(sr.getVal());
			flagD = sr.isFlag();
		} else {
			PacsRow pr = prr.findByPrimaryKey(galaxy, fluxDen, aperD);
			den = Float.parseFloat(pr.getVal());
			flagD = pr.isFlag();
		}
		
		float ratio = num/den;
		String limit = null;
		if (flagN && !flagD) {
			limit = "upper limit";
		}else if (!flagN && flagD) {
			limit = "lower limit";
		} else if (flagN && flagD) {
			limit = "sia numeratore che denominatore sono upper limit";
		} else if (!flagN && !flagD) {
			limit = "valore esatto";
		}
		String[][] result = {{String.valueOf(ratio), limit}};
		return result;
	}
	
	public String[][] fluxContRowRatio (String galaxy, String ion, String aperture) throws Exception {
		GalaxyRepository gr = new GalaxyRepository();
		if (gr.findByPrimaryKey(galaxy) == null) {
			throw new GalaxyNotExistsException(galaxy);
		}
		PacsRowRepository prr = new PacsRowRepository();
		PacsContRepository pcr = new PacsContRepository();
		PacsContinuousRow cont = null;
		PacsRow row = null;
		cont = pcr.findByPrimaryKey(galaxy, ion);
		if (cont == null) {
			throw new FluxNotExistsException(galaxy, 2);
		}
		if (aperture == null) {
			aperture = cont.getAperture();
		}
		row = prr.findByPrimaryKey(galaxy, ion, aperture);
		if (row == null) {
			throw new FluxNotExistsException(galaxy, 1);
		}
		float ratio = Float.parseFloat(row.getVal())/Float.parseFloat(cont.getVal());
		String limit = null;
		if (row.isFlag() && !cont.isFlag()) {
			limit = "upper limit";
		}else if (!row.isFlag() && cont.isFlag()) {
			limit = "lower limit";
		} else if (row.isFlag() && cont.isFlag()) {
			limit = "sia numeratore che denominatore sono upper limit";
		} else if (!row.isFlag() && !cont.isFlag()) {
			limit = "valore esatto";
		}
		String[][] result = {{String.valueOf(ratio), limit}};
		return result;
	}

	public String[][] fluxStats(String spetClass, String flux1, String flux2, String aper, int operType) throws Exception {
		GalaxyRepository gr = new GalaxyRepository();
		if (!gr.existSpectralClass(spetClass)) {
			throw new ClassNotExistsException();
		}
		PacsRowRepository prr = new PacsRowRepository();
		SpitzerRowRepository spr = new SpitzerRowRepository();
		List<Float> num = new ArrayList<Float>();
		List<Float> den = new ArrayList<Float>();
		List<Float> stats = new ArrayList<Float>();
		if(spr.findIon(flux1)) {
			num = spr.findAllRowOfClass(flux1, spetClass);
			if (num.isEmpty()) {
				throw new FluxNotExistsException(3);
			}
		} else if (prr.findIon(flux1)) {
			num = prr.findAllRowOfClass(flux1, spetClass, aper);
			if (num.isEmpty()) {
				throw new FluxNotExistsException(3);
			}
		} else {
			throw new FluxNotExistsException(1);
		}
		if(spr.findIon(flux2)) {
			den = spr.findAllRowOfClass(flux2, spetClass);
			if (den.isEmpty()) {
				throw new FluxNotExistsException(4);
			}
		} else if (prr.findIon(flux2)) {
			den = prr.findAllRowOfClass(flux2, spetClass, aper);
			if (den.isEmpty()) {
				throw new FluxNotExistsException(4);
			}
		} else {
			throw new FluxNotExistsException(2);
		}
		for (float n : num) {
			for (float d : den) {
				stats.add(n/d);
			}
		}
		String[][] result = {{"", "", "", ""}};
		if (operType == 1 || operType == 5 || operType == 3) {
			//average
			float sum = 0;
			for (float s : stats) {
				sum += s;
			}
			result[0][0] = String.valueOf(sum/stats.size());
		}
		if (operType == 2 || operType == 5 || operType == 4) {
			//median
			Collections.sort(stats);
			if (stats.size()%2 == 0) {
				result[0][1] = String.valueOf( (stats.get((stats.size()/2) -1) + stats.get(stats.size()/2)) / 2 );
			} else {
				result[0][1] = String.valueOf(stats.get(stats.size()/2));
			}
		}
		if (operType == 3 || operType == 5) {
			//standard deviation
			float rad = 0;
			float avg = Float.parseFloat(result[0][0]);
			for (float s : stats) {
				rad += Math.pow((s-avg), 2);
			}
			rad = rad/stats.size();
			result[0][2] = String.valueOf(Math.sqrt(rad));
		}
		if (operType == 4 || operType == 5) {
			//absolute average deviation
			float med = Float.parseFloat(result[0][1]);
			for (float s : stats) {
				s = s - med;
			}
			Collections.sort(stats);
			result[0][3] = String.valueOf(stats.get(stats.size()/2));
		}
		return result;
	}

	public String[][] findFluxes(String galaxy, String[] fluxes) throws Exception{
		GalaxyRepository gr = new GalaxyRepository();
		if (gr.findByPrimaryKey(galaxy) == null) {
			throw new GalaxyNotExistsException();
		}
		List<String> notFound = new ArrayList<String>();
		List<String> contFlux = new ArrayList<String>();
		List<String> rowFlux = new ArrayList<String>();
		List<String> contErr = new ArrayList<String>();
		List<String> rowErr = new ArrayList<String>();
		List<String> rowAp = new ArrayList<String>();
		List<String> contName = new ArrayList<String>();
		List<String> rowName = new ArrayList<String>();
		PacsRowRepository prr = new PacsRowRepository();
		PacsContRepository pcr = new PacsContRepository();
		SpitzerRowRepository srr = new SpitzerRowRepository();
		for (String flux : fluxes) {
			boolean found = false;
			if (srr.findByPrimaryKey(galaxy, flux) != null) {
				found = true;
				SpitzerRow sp = srr.findByPrimaryKey(galaxy, flux);
				rowFlux.add(sp.getVal());
				if (sp.isFlag()) {
					rowErr.add("0");
				} else {
					rowErr.add(sp.getErr());
				}
				rowAp.add("/");
				rowName.add(flux);
			}
			if (prr.findByPrimaryKey(galaxy, flux, "3x3") != null) {
				found = true;
				PacsRow pr = prr.findByPrimaryKey(galaxy, flux, "3x3");
				rowFlux.add(pr.getVal());
				if (pr.isFlag()) {
					rowErr.add("0");
				} else {
					rowErr.add(pr.getErr());
				}
				rowAp.add("3x3");
				rowName.add(flux);
			}
			if (prr.findByPrimaryKey(galaxy, flux, "5x5") != null) {
				found = true;
				PacsRow pr = prr.findByPrimaryKey(galaxy, flux, "5x5");
				rowFlux.add(pr.getVal());
				if (pr.isFlag()) {
					rowErr.add("0");
				} else {
					rowErr.add(pr.getErr());
				}
				rowAp.add("5x5");
				rowName.add(flux);
			}
			if (prr.findByPrimaryKey(galaxy, flux, "c") != null) {
				found = true;
				PacsRow pr = prr.findByPrimaryKey(galaxy, flux, "c");
				rowFlux.add(pr.getVal());
				if (pr.isFlag()) {
					rowErr.add("0");
				} else {
					rowErr.add(pr.getErr());
				}
				rowAp.add("c");
				rowName.add(flux);
			}
			if (pcr.findByPrimaryKey(galaxy, flux) != null) {
				found = true;
				PacsContinuousRow cr = pcr.findByPrimaryKey(galaxy, flux);
				contFlux.add(cr.getVal());
				if (cr.isFlag()) {
					contErr.add("0");
				} else {
					contErr.add(cr.getErr());
				}
				contName.add(flux);
			}
			if (!found) {
				notFound.add(flux);
			}
		}
		String[] notFoundA = new String[notFound.size()];
		String[] contFluxA = new String[contFlux.size()];
		String[] rowFluxA = new String[rowFlux.size()];
		String[] contErrA = new String[contErr.size()];
		String[] rowErrA = new String[rowErr.size()];
		String[] rowApA = new String[rowAp.size()];
		String[] contNameA = new String[contName.size()];
		String[] rowNameA = new String[rowName.size()];
		String[][] result = {notFound.toArray(notFoundA), contFlux.toArray(contFluxA), contErr.toArray(contErrA), contName.toArray(contNameA), 
					 rowFlux.toArray(rowFluxA),  rowErr.toArray(rowErrA), rowAp.toArray(rowApA), rowName.toArray(rowNameA)};
		return result;
		
	}
}


