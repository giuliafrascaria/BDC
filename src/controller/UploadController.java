package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import boundary.WaitingGUI;
import entity.AlternativeName;
import entity.Brightness;
import entity.Galaxy;
import entity.Position;
import entity.SpitzerRow;
import persistence.AltNameRepository;
import persistence.BrightnessRepository;
import persistence.GalaxyRepository;
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
		private long time;
		
		public Upload(String filePath, int fileType) {
			this.filePath = filePath;
			this.fileType = fileType;
		}	

		@Override
		public void run() {
			waiting = new WaitingGUI();
			
			time = Calendar.getInstance().getTimeInMillis();
			
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
				JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, il file potrebbe non essere stato caricato completamente, riprovare più tardi.", "Errore", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
			long currentTime = Calendar.getInstance().getTimeInMillis() - time;
			
			waiting.dispose();
			
			JOptionPane.showMessageDialog(null, "Operazione Completata con successo !\nProcesso terminato in: " + String.valueOf((int) currentTime/1000)
					+ " secondi", "Operazione completata", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		private void readGalaxies() throws Exception {
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			String name = "",rah = "", ram = "", ras = "", de = "", ded = "",
						dem = "", des = "", red = "", d = "", sp = "", l_lnev1 = "",
						l_lnev2 = "", l_loiv = "", lnev = "", lne2 = "", lnoiv = "",
						z = "", e_z = "", aName = "";
			
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
				String[] strArray = crunchifyLine.split(";");
										
				name = strArray[0].trim();
				rah = strArray[1].trim();
				ram = strArray[2].trim();
				ras = strArray[3].trim();
				de = strArray[4].trim();
				ded = strArray[5].trim();
				dem = strArray[6].trim();
				des = strArray[7].trim();
				red = strArray[8].trim();
				d = strArray[9].trim();
				sp = strArray[11].trim();
				l_lnev1 = strArray[16].trim();
				lnev = strArray[17].trim();
				l_lnev2 = strArray[18].trim();
				lne2 = strArray[19].trim();
				l_loiv = strArray[20].trim();
				lnoiv = strArray[21].trim();
				z = strArray[22].trim();
				e_z = strArray[23].trim();
				aName = strArray[25].trim();
						
				/*System.out.println(name + " " + rah + " " + ram + " " + ras + " " + de + " " + ded + " " + dem + " " + des + " " + red + " " + d + " " + sp + " " + l_lnev1 + " " + lnev + " " + l_lnev2 + " " + lne2 + " " + l_loiv + " " + lnoiv + " " +	z + " " + e_z + " " + aName);
				System.out.println();
					
				//aspetto per prova della finestra di attesa
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//*/

					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}
				
				Galaxy galaxy = new Galaxy();
				if (name.equals("")) {
					System.out.println("non dovrei");
					//errore la galsssia deve avere un nome
					JOptionPane.showMessageDialog(null, "Alcune galassie mancano del nome e non possono essere salvate", "Errore", JOptionPane.ERROR_MESSAGE);
					continue;
				}
				galaxy.setName(name);
				if (!d.equals("")) {
					galaxy.setDistance(Float.parseFloat(d));
				}
				if (!e_z.equals("")) {
					galaxy.setMetalErr(Float.parseFloat(e_z));
				}
				if (!z.equals("")) {
					galaxy.setMetalVal(Float.parseFloat(z));
				}
				if (sp.equals("")) {
					//errore deve esserci sempre classe spettrale
					JOptionPane.showMessageDialog(null, "La galassia " + name + " non è stata salvata perchè manca di classe spettrale", "Errore", JOptionPane.ERROR_MESSAGE);
					continue;
				}
				galaxy.setSpectralClass(sp);
				
				Position pos = new Position();
				if (rah.equals("") || ram.equals("") || ras.equals("") || de.equals("") || ded.equals("") || dem.equals("") || des.equals("") || red.equals("")) {
					//errore deve esserci sempre la posizione
					JOptionPane.showMessageDialog(null, "La galassia " + name + " non è stata salvata perchè manca di posizione", "Errore", JOptionPane.ERROR_MESSAGE);
					continue;
				}
				pos.setGalaxy(name);
				pos.setRaH(Float.parseFloat(rah));
				pos.setRaM(Float.parseFloat(ram));
				pos.setRaS(Float.parseFloat(ras));
				if (de.equals("+")) {
					pos.setDeSgn(true);
				} else {
					pos.setDeSgn(false);
				}
				pos.setDeD(Float.parseFloat(ded));
				pos.setDeM(Float.parseFloat(dem));
				pos.setDeS(Float.parseFloat(des));
				pos.setRedShift(Float.parseFloat(red));
				
				GalaxyRepository gr = new GalaxyRepository();
				gr.persist(galaxy);
				
				PositionRepository pr = new PositionRepository();
				pr.persist(pos);
				
				BrightnessRepository br = new BrightnessRepository();
				if (!lnev.equals("")) {
					Brightness br1 = new Brightness();
					br1.setIon("NeV14.3");
					if (l_lnev1.equals("")) {
						br1.setFlag(false);
					} else {
						br1.setFlag(true);
					}
					br1.setVal(Float.parseFloat(lnev));
					br1.setGalaxy(name);
					br.persist(br1);
				}
				
				if (!lne2.equals("")) {
					Brightness br2 = new Brightness();
					br2.setIon("NeV24.3");
					if (l_lnev2.equals("")) {
						br2.setFlag(false);
					} else {
						br2.setFlag(true);
					}
					br2.setVal(Float.parseFloat(lne2));
					br2.setGalaxy(name);
					br.persist(br2);
				}
				
				if (!lnoiv.equals("")) {
					Brightness br3 = new Brightness();
					br3.setIon("OIV25.9");
					if (l_loiv.equals("")) {
						br3.setFlag(false);
					} else {
						br3.setFlag(true);
					}
					br3.setVal(Float.parseFloat(lnoiv));
					br3.setGalaxy(name);
					br.persist(br3);
				}
				
				String[] strAltNames = aName.split(",");
				AltNameRepository ar = new AltNameRepository();
				for (String n : strAltNames) {
					n = n.trim();
					AlternativeName alt = new AlternativeName();
					alt.setName(n);
					alt.setGalaxy(name);
					ar.persist(alt);
				}
				
			}
			crunchifyBuffer.close();
		}
		
		private void readSpitzer() throws Exception {
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			String name = "", l_Fsiv10 = "", Fsiv10 = "", e_Fsiv10 = "", l_Fneii12 = "", Fneii12 = "", e_Fneii12 = "",
							l_Fnev14 = "", Fnev14 = "", e_Fnev14 = "", l_Fneiii15 = "", Fneiii15 = "", e_Fneiii15 = "",
							l_Fsiii18 = "", Fsiii18 = "", e_Fsiii18 = "", l_Fnev24 = "", Fnev24 = "", e_Fnev24 = "",
							l_Foiv25 = "", Foiv25 = "", e_Foiv25 = "", l_Fsiii33 = "", Fsiii33 = "", e_Fsiii33 = "", 
							l_Fsii34 = "", Fsii34 = "", e_Fsii34 = "", Mod = "";
				
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					
				String[] strArray = crunchifyLine.split(";");
										
				name = strArray[0].trim();
				l_Fsiv10 = strArray[1].trim(); 
				Fsiv10 = strArray[2].trim();
				e_Fsiv10 = strArray[3].trim();
				l_Fneii12 = strArray[4].trim();
				Fneii12 = strArray[5].trim();
				e_Fneii12 = strArray[6].trim();
				l_Fnev14 = strArray[7].trim();
				Fnev14 = strArray[8].trim();
				e_Fnev14 = strArray[9].trim();
				l_Fneiii15 = strArray[10].trim();
				Fneiii15 = strArray[11].trim();
				e_Fneiii15 = strArray[12].trim();
				l_Fsiii18 = strArray[13].trim();
				Fsiii18 = strArray[14].trim();
				e_Fsiii18 = strArray[15].trim();
				l_Fnev24 = strArray[16].trim();
				Fnev24 = strArray[17].trim();
				e_Fnev24 = strArray[18].trim();
				l_Foiv25 = strArray[19].trim();
				Foiv25 = strArray[20].trim();
				e_Foiv25 = strArray[21].trim();
				l_Fsiii33 = strArray[22].trim();
				Fsiii33 = strArray[23].trim();
				e_Fsiii33 = strArray[24].trim();
				l_Fsii34 = strArray[25].trim();
				Fsii34 = strArray[26].trim();
				e_Fsii34 = strArray[27].trim();
				Mod = strArray[28].trim();
				
				String[] ionArray = {"Fsiv10", "Fneii12", "Fnev14", "Fneiii15", "Fsiii18", "Fnev24", "Foiv25", "Fsiii33", "Fsii34"};
				String[] flagArray = {l_Fsiv10, l_Fneii12, l_Fnev14, l_Fneiii15, l_Fsiii18, l_Fnev24, l_Foiv25, l_Fsiii33, l_Fsii34};
				String[] valArray = {Fsiv10, Fneii12, Fnev14, Fneiii15, Fsiii18, Fnev24, Foiv25, Fsiii33, Fsii34};
				String[] errArray = {e_Fsiv10, e_Fneii12, e_Fnev14, e_Fneiii15, e_Fsiii18, e_Fnev24, e_Foiv25, e_Fsiii33, e_Fsii34};
				
				/*System.out.println(name + " " + l_Fsiv10 + " " + Fsiv10 + " " + e_Fsiv10 + " " + l_Fneii12 + " " + Fneii12 + " " + e_Fneii12 + " " + l_Fnev14 + " " + Fnev14 + " " + e_Fnev14 + " " + l_Fneiii15 + " " + Fneiii15 + " " + e_Fneiii15 + 
						" " + l_Fsiii18 + " " + Fsiii18 + " " + e_Fsiii18 + " " + l_Fnev24 + " " +	Fnev24 + " " + e_Fnev24 + " " + l_Foiv25 + " " + Foiv25 + " " + e_Foiv25 + " " + l_Fsiii33 + " " + Fsiii33 + " " + e_Fsiii33 + " " + l_Fsii34 + " " + Fsii34 + " " + e_Fsii34 + " " + Mod);
				System.out.println();
					
				//aspetto per prova della finestra di attesa
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//*/

					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}
				
				SpitzerRowRepository spr = new SpitzerRowRepository();
				for (int i=0; i < ionArray.length; i++) {
					//bisogna vedere i controlli degli errori
					SpitzerRow row = new SpitzerRow();
					row.setGalaxy(name);
					row.setIon(ionArray[i]);
					row.setErr(Float.parseFloat(errArray[i]));
					row.setVal(Float.parseFloat(valArray[i]));
					if (flagArray[i].equals("")) {
						row.setFlag(false);
					} else {
						row.setFlag(true);
					}
					spr.persist(row);
				}
				GalaxyRepository gr = new GalaxyRepository();
			}
			crunchifyBuffer.close();
		}
		
		private void readContPacs() throws IOException{
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			String name = "", Coiii52 = "", e_Coiii52 = "", Cniii57 = "", e_Cniii57 = "",
							l_Coi63 = "", Coi63 = "", e_Coi63 = "", l_Coiii88 = "", Coiii88 = "", e_Coiii88 = "",
							l_Cnii122 = "", Cnii122 = "", e_Cnii122 = "", l_Coi145 = "", Coi145 = "", e_Coi145 = "",
							l_Ccii158 = "", Ccii158 = "", e_Ccii158 = "", Aper = "";
				
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					
				String[] strArray = crunchifyLine.split(";");
				
				//crea le entità galassia posizione e luminosità che ti servono
						
				name = strArray[0].trim();
				Coiii52 = strArray[1].trim(); 
				e_Coiii52 = strArray[2].trim();
				Cniii57 = strArray[3].trim();
				e_Cniii57 = strArray[4].trim();
				l_Coi63 = strArray[5].trim();
				Coi63 = strArray[6].trim();
				e_Coi63 = strArray[7].trim();
				l_Coiii88 = strArray[8].trim();
				Coiii88 = strArray[9].trim();
				e_Coiii88 = strArray[10].trim();
				l_Cnii122 = strArray[11].trim();
				Cnii122 = strArray[12].trim();
				e_Cnii122 = strArray[13].trim();
				l_Coi145 = strArray[14].trim();
				Coi145 = strArray[15].trim();
				e_Coi145 = strArray[16].trim();
				l_Ccii158 = strArray[17].trim();
				Ccii158 = strArray[18].trim();
				e_Ccii158 = strArray[19].trim();
				Aper = strArray[21].trim();
						
				System.out.println(name + " " + Coiii52 + " " + e_Coiii52 + " " + Cniii57 + " " + e_Cniii57 + " " + l_Coi63 + " " + Coi63 + " " + e_Coi63 + " " + l_Coiii88 + " " + Coiii88 + " " + e_Coiii88 + " " + 
						" " + l_Cnii122 + " " + Cnii122 + " " + e_Cnii122 + " " + l_Coi145 + " " +	Coi145 + " " + e_Coi145 + " " + l_Ccii158 + " " + Ccii158 + " " + e_Ccii158 + " " + Aper + " ");
				System.out.println();
					
				//aspetto per prova della finestra di attesa
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//

					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}		
						
				//aggiungi i valori alle varie entità e salvale nel db				
			}
			crunchifyBuffer.close();
		}
		
		private void readRowPacs() throws IOException{
			BufferedReader crunchifyBuffer = null;
			
			String crunchifyLine;
			
			crunchifyBuffer = new BufferedReader(new FileReader(filePath));
			
			String name = "", l_Foiii52 = "", Foiii52 = "", e_Foiii52 = "", l_Fniii57 = "", Fniii57 = "", e_Fniii57 = "", 
							l_Foi63 = "", Foi63 = "", e_Foi63 = "", l_Foiii88 = "", Foiii88 = "", e_Foiii88 = "", 
							l_Fnii122 = "", Fnii122 = "", e_Fnii122 = "", l_Foi145 = "", Foi145 = "", e_Foi145 = "", 
							l_Fcii158 = "", Fcii158 = "", e_Fcii158 = "", Aper = "";
				
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
					
				String[] strArray = crunchifyLine.split(";");				
				//crea le entità galassia posizione e luminosità che ti servono
						
				name = strArray[0].trim();
				l_Foiii52 = strArray[1].trim(); 
				Foiii52 = strArray[2].trim();
				e_Foiii52 = strArray[3].trim();
				l_Fniii57 = strArray[4].trim();
				Fniii57 = strArray[5].trim();
				e_Fniii57 = strArray[6].trim();
				l_Foi63 = strArray[7].trim();
				Foi63 = strArray[8].trim();
				e_Foi63 = strArray[9].trim();
				l_Foiii88 = strArray[10].trim();
				Foiii88 = strArray[11].trim();
				e_Foiii88 = strArray[12].trim();
				l_Fnii122 = strArray[13].trim();
				Fnii122 = strArray[14].trim();
				e_Fnii122 = strArray[15].trim();
				l_Foi145 = strArray[16].trim();
				Foi145 = strArray[17].trim();
				e_Foi145 = strArray[18].trim();
				l_Fcii158 = strArray[19].trim();
				Fcii158 = strArray[20].trim();
				e_Fcii158 = strArray[21].trim();
				Aper = strArray[22].trim();
						
				System.out.println(name + " " + l_Foiii52 + " " + Foiii52 + " " + e_Foiii52 + " " + l_Fniii57 + " " + Fniii57 + " " + e_Fniii57 + " " + l_Foi63 + " " + Foi63 + " " + e_Foi63 + " " + 
						" " + l_Foiii88 + " " + Foiii88 + " " + e_Foiii88 + " " + l_Fnii122 + " " +	Fnii122 + " " + e_Fnii122 + " " + l_Foi145 + " " + Foi145 + " " + e_Foi145 + l_Fcii158 + " " + Fcii158 + " " + e_Fcii158  + " " + Aper + " ");
				System.out.println();
					
				//aspetto per prova della finestra di attesa
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//

					
				if( name == null || name == ""){ //end of the file, so break
					break;
				}		
						
				//aggiungi i valori alle varie entità e salvale nel db				
			}
			crunchifyBuffer.close();
		}
		
	}
}