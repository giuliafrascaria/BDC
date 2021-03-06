package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import boundary.WaitingGUI;
import entity.AlternativeName;
import entity.Brightness;
import entity.Galaxy;
import entity.PacsContinuousRow;
import entity.PacsRow;
import entity.Position;
import entity.SpitzerRow;
import exceptions.GalaxyNotExistsException;
import persistence.AltNameRepository;
import persistence.BrightnessRepository;
import persistence.GalaxyRepository;
import persistence.PacsContRepository;
import persistence.PacsRowRepository;
import persistence.PositionRepository;
import persistence.SpitzerRowRepository;

public class UploadController {

	private String filePath = "";	
	private int fileType;
			
	private WaitingGUI waiting;
	private static UploadController instance = null;

    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return FileUploadController the current controller in the system
     */
    public static final synchronized UploadController getInstance() {
        if (instance == null)
            instance = new UploadController();
        return instance;
    }
    
    public void setFilePath(String filePath) {
    	this.filePath = filePath;
    }
    
    public void setFileType (int fileType) {
    	this.fileType = fileType;
    }
	
	public void uploadFile() {
		Thread t = new Thread(new Upload(filePath, fileType));
		t.start();
		
	}
	
	private class Upload implements Runnable {
		private String filePath = "";	
		private int fileType;
		
		public Upload(String filePath, int fileType) {
			this.filePath = filePath;
			this.fileType = fileType;
		}	

		@Override
		public void run() {
			waiting = new WaitingGUI();
						
			try {
				switch (fileType){
				case 1: readGalaxies(); break;
				case 2: readRowPacs(); break;
				case 3: readContPacs(); break;
				case 4: readSpitzer(); break;
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Il file immesso non esiste", "Errore", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, il file potrebbe non essere stato caricato completamente, riprovare più tardi.", "Errore", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, il file potrebbe non essere stato caricato completamente.\nAssicurarsi di aver selezionato il tipo di file corretto o riprovare più tardi.", "Errore", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
						
			waiting.dispose();
		}
		
		private void readGalaxies() throws Exception {
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			if (crunchifyBuffer.readLine().split(";").length != 26) {
				JOptionPane.showMessageDialog(null, "Il file inserito non è del tipo selezionato.\nIl file galassie deve avere 26 campi per riga", "Errore", JOptionPane.ERROR_MESSAGE);
				crunchifyBuffer.close();
				return;
			}
			
			String name = "",rah = "", ram = "", ras = "", de = "", ded = "",
						dem = "", des = "", red = "", d = "", sp = "", l_lnev1 = "",
						l_lnev2 = "", l_loiv = "", lnev = "", lne2 = "", lnoiv = "",
						z = "", e_z = "", aName = "";
			
			boolean allSaved = true;
			
			GalaxyRepository gr = new GalaxyRepository();
			PositionRepository pr = new PositionRepository();
			BrightnessRepository br = new BrightnessRepository();
			AltNameRepository ar = new AltNameRepository();
			
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
				String[] strArray = crunchifyLine.split(";");
										
				name = strArray[0].trim().toLowerCase();
				rah = strArray[1].trim().toLowerCase();
				ram = strArray[2].trim().toLowerCase();
				ras = strArray[3].trim().toLowerCase();
				de = strArray[4].trim().toLowerCase();
				ded = strArray[5].trim().toLowerCase();
				dem = strArray[6].trim().toLowerCase();
				des = strArray[7].trim().toLowerCase();
				red = strArray[8].trim().toLowerCase();
				d = strArray[9].trim().toLowerCase();
				sp = strArray[11].trim().toLowerCase();
				l_lnev1 = strArray[16].trim().toLowerCase();
				lnev = strArray[17].trim().toLowerCase();
				l_lnev2 = strArray[18].trim().toLowerCase();
				lne2 = strArray[19].trim().toLowerCase();
				l_loiv = strArray[20].trim().toLowerCase();
				lnoiv = strArray[21].trim().toLowerCase();
				z = strArray[22].trim().toLowerCase();
				e_z = strArray[23].trim().toLowerCase();
				aName = strArray[25].trim().toLowerCase();

					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}
				
				Galaxy galaxy = new Galaxy();
				if (name.equals("")) {
					allSaved = false;
					continue;
				}
				galaxy.setName(name);
				galaxy.setDistance(d);
				galaxy.setMetalErr(e_z);
				galaxy.setMetalVal(z);
				if (sp.equals("")) {
					allSaved = false;
					continue;
				}
				galaxy.setSpectralClass(sp);
				
				Position pos = new Position();
				if (rah.equals("") || ram.equals("") || ras.equals("") || de.equals("") || ded.equals("") || dem.equals("") || des.equals("") || red.equals("")) {
					allSaved = false;
					continue;
				}
				pos.setGalaxy(name);
				pos.setRaH(rah);
				pos.setRaM(ram);
				pos.setRaS(ras);
				if (de.equals("+")) {
					pos.setDeSgn(true);
				} else {
					pos.setDeSgn(false);
				}
				pos.setDeD(ded);
				pos.setDeM(dem);
				pos.setDeS(des);
				pos.setRedShift(red);
				
				gr.persist(galaxy);
				
				pr.persist(pos);
				
				if (!lnev.equals("")) {
					Brightness br1 = new Brightness();
					br1.setIon("nev14.3");
					if (l_lnev1.equals("")) {
						br1.setFlag(false);
					} else {
						br1.setFlag(true);
					}
					br1.setVal(lnev);
					br1.setGalaxy(name);
					br.persist(br1);
				}
				
				if (!lne2.equals("")) {
					Brightness br2 = new Brightness();
					br2.setIon("nev24.3");
					if (l_lnev2.equals("")) {
						br2.setFlag(false);
					} else {
						br2.setFlag(true);
					}
					br2.setVal(lne2);
					br2.setGalaxy(name);
					br.persist(br2);
				}
				
				if (!lnoiv.equals("")) {
					Brightness br3 = new Brightness();
					br3.setIon("oiv25.9");
					if (l_loiv.equals("")) {
						br3.setFlag(false);
					} else {
						br3.setFlag(true);
					}
					br3.setVal(lnoiv);
					br3.setGalaxy(name);
					br.persist(br3);
				}
				
				if (!aName.equals("")) {
					String[] strAltNames = aName.split(",");
					for (String n : strAltNames) {
						n = n.trim();
						AlternativeName alt = new AlternativeName();
						alt.setName(n);
						alt.setGalaxy(name);
						ar.persist(alt);
					}
				}
			}
			crunchifyBuffer.close();
			if (!allSaved) {
				JOptionPane.showMessageDialog(null, "Alcune galassie non sono state salvate, mancano di campi fondamentali (nome, valori della posizione, classe spettrale)", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		private void readSpitzer() throws Exception {
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			if (crunchifyBuffer.readLine().split(";").length != 30) {
				JOptionPane.showMessageDialog(null, "Il file inserito non è del tipo selezionato.\nIl file Spitzer deve avere 30 campi per riga", "Errore", JOptionPane.ERROR_MESSAGE);
				crunchifyBuffer.close();
				return;
			}
			
			String name = "", l_Fsiv10 = "", Fsiv10 = "", e_Fsiv10 = "", l_Fneii12 = "", Fneii12 = "", e_Fneii12 = "",
							l_Fnev14 = "", Fnev14 = "", e_Fnev14 = "", l_Fneiii15 = "", Fneiii15 = "", e_Fneiii15 = "",
							l_Fsiii18 = "", Fsiii18 = "", e_Fsiii18 = "", l_Fnev24 = "", Fnev24 = "", e_Fnev24 = "",
							l_Foiv25 = "", Foiv25 = "", e_Foiv25 = "", l_Fsiii33 = "", Fsiii33 = "", e_Fsiii33 = "", 
							l_Fsii34 = "", Fsii34 = "", e_Fsii34 = "", Mod = "";
			
			GalaxyRepository gr = new GalaxyRepository();
			
			boolean allSaved = true;
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
				String[] strArray = crunchifyLine.split(";");
				
				name = strArray[0].trim().toLowerCase();
				l_Fsiv10 = strArray[1].trim().toLowerCase(); 
				Fsiv10 = strArray[2].trim().toLowerCase();
				e_Fsiv10 = strArray[3].trim().toLowerCase();
				l_Fneii12 = strArray[4].trim().toLowerCase();
				Fneii12 = strArray[5].trim().toLowerCase();
				e_Fneii12 = strArray[6].trim().toLowerCase();
				l_Fnev14 = strArray[7].trim().toLowerCase();
				Fnev14 = strArray[8].trim().toLowerCase();
				e_Fnev14 = strArray[9].trim().toLowerCase();
				l_Fneiii15 = strArray[10].trim().toLowerCase();
				Fneiii15 = strArray[11].trim().toLowerCase();
				e_Fneiii15 = strArray[12].trim().toLowerCase();
				l_Fsiii18 = strArray[13].trim().toLowerCase();
				Fsiii18 = strArray[14].trim().toLowerCase();
				e_Fsiii18 = strArray[15].trim().toLowerCase();
				l_Fnev24 = strArray[16].trim().toLowerCase();
				Fnev24 = strArray[17].trim().toLowerCase();
				e_Fnev24 = strArray[18].trim().toLowerCase();
				l_Foiv25 = strArray[19].trim().toLowerCase();
				Foiv25 = strArray[20].trim().toLowerCase();
				e_Foiv25 = strArray[21].trim().toLowerCase();
				l_Fsiii33 = strArray[22].trim().toLowerCase();
				Fsiii33 = strArray[23].trim().toLowerCase();
				e_Fsiii33 = strArray[24].trim().toLowerCase();
				l_Fsii34 = strArray[25].trim().toLowerCase();
				Fsii34 = strArray[26].trim().toLowerCase();
				e_Fsii34 = strArray[27].trim().toLowerCase();
				Mod = strArray[28].trim().toLowerCase();
				
				String[] ionArray = {"siv10", "neii12", "nev14", "neiii15", "siii18", "nev24", "oiv25", "siii33", "sii34"};
				String[] flagArray = {l_Fsiv10, l_Fneii12, l_Fnev14, l_Fneiii15, l_Fsiii18, l_Fnev24, l_Foiv25, l_Fsiii33, l_Fsii34};
				String[] valArray = {Fsiv10, Fneii12, Fnev14, Fneiii15, Fsiii18, Fnev24, Foiv25, Fsiii33, Fsii34};
				String[] errArray = {e_Fsiv10, e_Fneii12, e_Fnev14, e_Fneiii15, e_Fsiii18, e_Fnev24, e_Foiv25, e_Fsiii33, e_Fsii34};
					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}
				
				SpitzerRowRepository spr = new SpitzerRowRepository();
				for (int i=0; i < ionArray.length; i++) {
					SpitzerRow row = new SpitzerRow();
					if (valArray[i].equals("")) {
						spr.delete(name, ionArray[i]);
						continue;
					}
					if (valArray[i].equals("")) {
						continue;
					}
					row.setGalaxy(name);
					row.setIon(ionArray[i]);
					row.setErr(errArray[i]);
					row.setVal(valArray[i]);
					if (flagArray[i].equals("")) {
						row.setFlag(false);
					} else {
						row.setFlag(true);
					}
					try {
						spr.persist(row);
					} catch (GalaxyNotExistsException e) {
						allSaved = false;
						continue;
					}
				}
				if (!Mod.equals("")) {
					gr.adIRS(name, Mod);
				}
			}
			crunchifyBuffer.close();
			if (!allSaved) {
				JOptionPane.showMessageDialog(null, "Alcuni flussi non sono stati salvati, poichè nel database non esiste la galassia a cui si riferiscono. Registrare la galassia e poi riprovare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		private void readContPacs() throws Exception{
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			if (crunchifyBuffer.readLine().split(";").length != 22) {
				JOptionPane.showMessageDialog(null, "Il file inserito non è del tipo selezionato.\nIl file Pacs continuo deve avere 22 campi per riga", "Errore", JOptionPane.ERROR_MESSAGE);
				crunchifyBuffer.close();
				return;
			}
			
			String name = "", Coiii52 = "", e_Coiii52 = "", Cniii57 = "", e_Cniii57 = "",
							l_Coi63 = "", Coi63 = "", e_Coi63 = "", l_Coiii88 = "", Coiii88 = "", e_Coiii88 = "",
							l_Cnii122 = "", Cnii122 = "", e_Cnii122 = "", l_Coi145 = "", Coi145 = "", e_Coi145 = "",
							l_Ccii158 = "", Ccii158 = "", e_Ccii158 = "", Aper = "";
				
			boolean allSaved = true;
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					
				String[] strArray = crunchifyLine.split(";");
										
				name = strArray[0].trim().toLowerCase();
				Coiii52 = strArray[1].trim().toLowerCase(); 
				e_Coiii52 = strArray[2].trim().toLowerCase();
				Cniii57 = strArray[3].trim().toLowerCase();
				e_Cniii57 = strArray[4].trim().toLowerCase();
				l_Coi63 = strArray[5].trim().toLowerCase();
				Coi63 = strArray[6].trim().toLowerCase();
				e_Coi63 = strArray[7].trim().toLowerCase();
				l_Coiii88 = strArray[8].trim().toLowerCase();
				Coiii88 = strArray[9].trim().toLowerCase();
				e_Coiii88 = strArray[10].trim().toLowerCase();
				l_Cnii122 = strArray[11].trim().toLowerCase();
				Cnii122 = strArray[12].trim().toLowerCase();
				e_Cnii122 = strArray[13].trim().toLowerCase();
				l_Coi145 = strArray[14].trim().toLowerCase();
				Coi145 = strArray[15].trim().toLowerCase();
				e_Coi145 = strArray[16].trim().toLowerCase();
				l_Ccii158 = strArray[17].trim().toLowerCase();
				Ccii158 = strArray[18].trim().toLowerCase();
				e_Ccii158 = strArray[19].trim().toLowerCase();
				Aper = strArray[21].trim().toLowerCase();
				
				String[] ionArray = {"oiii52", "niii57", "oi63", "oiii88", "nii122", "oi145", "cii158"};
				String[] flagArray = {"", "", l_Coi63, l_Coiii88, l_Cnii122, l_Coi145, l_Ccii158};
				String[] valArray = {Coiii52, Cniii57, Coi63, Coiii88, Cnii122, Coi145, Ccii158};
				String[] errArray = {e_Coiii52, e_Cniii57, e_Coi63, e_Coiii88, e_Cnii122, e_Coi145, e_Ccii158};
					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}		
						
				PacsContRepository pcr = new PacsContRepository();
				for (int i=0; i < ionArray.length; i++) {
					PacsContinuousRow cont = new PacsContinuousRow();
					if (valArray[i].equals("")) {
						pcr.delete(name, ionArray[i]);
						continue;
					}
					cont.setGalaxy(name);
					cont.setIon(ionArray[i]);
					cont.setErr(errArray[i]);
					cont.setVal(valArray[i]);
					if (flagArray[i].equals("")) {
						cont.setFlag(false);
					} else {
						cont.setFlag(true);
					}
					cont.setAperture(Aper);
					try {
						pcr.persist(cont);
					} catch (GalaxyNotExistsException e) {
						allSaved = false;
						continue;
					}
				}
			}
			crunchifyBuffer.close();
			if (!allSaved) {
				JOptionPane.showMessageDialog(null, "Alcuni flussi non sono stati salvati, poichè nel database non esiste la galassia a cui si riferiscono. Registrare la galassia e poi riprovare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		private void readRowPacs() throws Exception{
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			if (crunchifyBuffer.readLine().split(";").length != 23) {
				JOptionPane.showMessageDialog(null, "Il file inserito non è del tipo selezionato.\nIl file Pacs riga deve avere 23 campi per riga", "Errore", JOptionPane.ERROR_MESSAGE);
				crunchifyBuffer.close();
				return;
			}
			
			String name = "", l_Foiii52 = "", Foiii52 = "", e_Foiii52 = "", l_Fniii57 = "", Fniii57 = "", e_Fniii57 = "", 
							l_Foi63 = "", Foi63 = "", e_Foi63 = "", l_Foiii88 = "", Foiii88 = "", e_Foiii88 = "", 
							l_Fnii122 = "", Fnii122 = "", e_Fnii122 = "", l_Foi145 = "", Foi145 = "", e_Foi145 = "", 
							l_Fcii158 = "", Fcii158 = "", e_Fcii158 = "", Aper = "";
				
			boolean allSaved = true;
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					
				String[] strArray = crunchifyLine.split(";");				
						
				name = strArray[0].trim().toLowerCase();
				l_Foiii52 = strArray[1].trim().toLowerCase(); 
				Foiii52 = strArray[2].trim().toLowerCase();
				e_Foiii52 = strArray[3].trim().toLowerCase();
				l_Fniii57 = strArray[4].trim().toLowerCase();
				Fniii57 = strArray[5].trim().toLowerCase();
				e_Fniii57 = strArray[6].trim().toLowerCase();
				l_Foi63 = strArray[7].trim().toLowerCase();
				Foi63 = strArray[8].trim().toLowerCase();
				e_Foi63 = strArray[9].trim().toLowerCase();
				l_Foiii88 = strArray[10].trim().toLowerCase();
				Foiii88 = strArray[11].trim().toLowerCase();
				e_Foiii88 = strArray[12].trim().toLowerCase();
				l_Fnii122 = strArray[13].trim().toLowerCase();
				Fnii122 = strArray[14].trim().toLowerCase();
				e_Fnii122 = strArray[15].trim().toLowerCase();
				l_Foi145 = strArray[16].trim().toLowerCase();
				Foi145 = strArray[17].trim().toLowerCase();
				e_Foi145 = strArray[18].trim().toLowerCase();
				l_Fcii158 = strArray[19].trim().toLowerCase();
				Fcii158 = strArray[20].trim().toLowerCase();
				e_Fcii158 = strArray[21].trim().toLowerCase();
				Aper = strArray[22].trim().toLowerCase();
				
				String[] ionArray = {"oiii52", "niii57", "oi63", "oiii88", "nii122", "oi145", "cii158"};
				String[] flagArray = {l_Foiii52, l_Fniii57, l_Foi63, l_Foiii88, l_Fnii122, l_Foi145, l_Fcii158};
				String[] valArray = {Foiii52, Fniii57, Foi63, Foiii88, Fnii122, Foi145, Fcii158};
				String[] errArray = {e_Foiii52, e_Fniii57, e_Foi63, e_Foiii88, e_Fnii122, e_Foi145, e_Fcii158};
					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}		
						
				PacsRowRepository prr = new PacsRowRepository();
				for (int i=0; i < ionArray.length; i++) {
					PacsRow row = new PacsRow();
					if (valArray[i].equals("")) {
						prr.delete(name, ionArray[i], Aper);
						continue;
					}
					row.setGalaxy(name);
					row.setIon(ionArray[i]);
					row.setErr(errArray[i]);
					row.setVal(valArray[i]);
					if (flagArray[i].equals("")) {
						row.setFlag(false);
					} else {
						row.setFlag(true);
					}
					row.setAperture(Aper);
					try {
						prr.persist(row);
					} catch (GalaxyNotExistsException e) {
						allSaved = false;
						continue;
					}
				}				
			}
			crunchifyBuffer.close();
			if (!allSaved) {
				JOptionPane.showMessageDialog(null, "Alcuni flussi non sono stati salvati, poichè nel database non esiste la galassia a cui si riferiscono. Registrare la galassia e poi riprovare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
}