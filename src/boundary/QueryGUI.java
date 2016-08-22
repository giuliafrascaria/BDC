package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.QueryController;
import exceptions.ClassNotExistsException;
import exceptions.FluxNotExistsException;
import exceptions.GalaxyNotExistsException;
import exceptions.PositionTableEmptyException;

public class QueryGUI {

	private int accountType;
	public JPanel mainPanel;
	private int queryType;
	
	private JTextField txtInput1;
	private JTextField txtInput2;
	private JTextField txtInput3;
	private JTextField txtInput4;
	private JLabel lblInput1;
	private JLabel lblInput2;
	private JLabel lblInput3;
	private JLabel lblInput4;
	public ButtonGroup aperture;
	public ButtonGroup operation;
	public ButtonGroup range;
	public JRadioButton rdbtnLess;
	public JRadioButton rdbtnGreater;
	public JRadioButton rdbtnEqual;
	public JRadioButton rdbtn3;
	public JRadioButton rdbtn5;
	public JRadioButton rdbtnC;
	public JRadioButton rdbtnSame;
	public JRadioButton rdbtnAvg;
	public JRadioButton rdbtnMed;
	public JRadioButton rdbtnDev;
	public JRadioButton rdbtnDevAvgAbs;
	public JRadioButton rdbtnAll;
	
	private LogoutAL LogoutActionListener;
	private HomeAL HomeActionListener;
	private BackAL BackActionListener;
	private QueryAL QueryActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public QueryGUI(int accountType, JPanel mainPanel, int queryType) {
		setMainPanel(mainPanel);
		mainPanel.removeAll();
		this.accountType = accountType;
		this.queryType = queryType;
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(195, 20, 363, 32);
		mainPanel.add(horizontalBox);
		
		JLabel lblEasyrent = new JLabel("Galaxies");
		horizontalBox.add(lblEasyrent);
		
		JButton btnHome = new JButton("Home");
		horizontalBox.add(btnHome);
		
		JButton btnLogout = new JButton("Logout");
		horizontalBox.add(btnLogout);
		
		JButton btnFaq = new JButton("FAQ");
		horizontalBox.add(btnFaq);
		
		JButton btnInfo = new JButton("About");
		horizontalBox.add(btnInfo);
		
		JLabel lblTitle = new JLabel("Compila i campi");
		lblTitle.setBounds(250, 75, 285, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JLabel lblSubTitle = new JLabel("");
		lblSubTitle.setBounds(138, 133, 510, 25);
		mainPanel.add(lblSubTitle);
		
		switch (queryType) {
			case 1: lblSubTitle.setText("Ricerca per nome");
			
					lblInput1 = new JLabel("nome della galassia:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);
	
					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);
					break;
			case 2: lblSubTitle.setText("Ricerca entro un raggio");
			
					lblInput1 = new JLabel("Numero massimo di galassie da restituire:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);
			
					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);
			
					lblInput2 = new JLabel("Raggio entro il quale cercare:");
					lblInput2.setBounds(106, 285, 228, 15);
					mainPanel.add(lblInput2);
			
					txtInput2 = new JTextField();
					txtInput2.setBounds(430, 285, 247, 19);
					mainPanel.add(txtInput2);
			
					JLabel lblCenter = new JLabel("COORDINATE DEL CENTRO (ascenzione e declinazione)");
					lblCenter.setBounds(106, 332, 400, 15);
					mainPanel.add(lblCenter);
					
					lblInput3 = new JLabel("Ascensione retta (ore_minuti_secondi):");
					lblInput3.setBounds(106, 360, 289, 15);
					mainPanel.add(lblInput3);
			
					txtInput3 = new JTextField();
					txtInput3.setBounds(430, 360, 247, 19);
					mainPanel.add(txtInput3);
			
					lblInput4 = new JLabel("Declinazione (segno_gradi_minuti_secondi):");
					lblInput4.setBounds(106, 435, 318, 15);
					mainPanel.add(lblInput4);
			
					txtInput4 = new JTextField();
					txtInput4.setBounds(430, 435, 247, 19);
					mainPanel.add(txtInput4);
					break;
			case 3: lblSubTitle.setText("Ricerca per caratteristiche fisiche");
					
					lblInput1 = new JLabel("Valore del redshift");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);
			
					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);
			
					lblInput2 = new JLabel("Range di risultati da visualizzare:");
					lblInput2.setBounds(106, 285, 228, 15);
					mainPanel.add(lblInput2);

					rdbtnLess = new JRadioButton("<");
					rdbtnLess.setBounds(430, 285, 65, 15);
					mainPanel.add(rdbtnLess);
					
					rdbtnGreater = new JRadioButton(">");
					rdbtnGreater.setBounds(520, 285, 65, 15);
					mainPanel.add(rdbtnGreater);
					
					rdbtnEqual = new JRadioButton("=");
					rdbtnEqual.setBounds(610, 285, 65, 15);
					mainPanel.add(rdbtnEqual);
					
					range = new ButtonGroup();
					range.add(rdbtnLess);
					range.add(rdbtnGreater);
					range.add(rdbtnEqual);
					
					lblInput3 = new JLabel("Numero massimo di risultati:");
					lblInput3.setBounds(106, 360, 200, 15);
					mainPanel.add(lblInput3);
					
					txtInput3 = new JTextField();
					txtInput3.setBounds(430, 360, 247, 19);
					mainPanel.add(txtInput3);
					
					break;
					
			case 4: lblSubTitle.setText("Ricerca linee spettrali");
					lblInput1 = new JLabel("Nome della galassia:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);
	
					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);
	
					lblInput2 = new JLabel("Flussi da trovare:");
					lblInput2.setBounds(106, 285, 228, 15);
					mainPanel.add(lblInput2);
	
					txtInput2 = new JTextField();
					txtInput2.setText("flusso1_flusso2_...._flussoN");
					txtInput2.setBounds(430, 285, 247, 19);
					mainPanel.add(txtInput2);		
					break;
			case 5: lblSubTitle.setText("Ricerca dei rapporti righe spettrali");
			
					lblInput1 = new JLabel("Nome della galassia:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);
	
					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);
	
					lblInput2 = new JLabel("Flusso al numeratore:");
					lblInput2.setBounds(106, 285, 228, 15);
					mainPanel.add(lblInput2);
	
					txtInput2 = new JTextField();
					txtInput2.setBounds(430, 285, 247, 19);
					mainPanel.add(txtInput2);
			
					lblInput3 = new JLabel("Flusso al denominatore:");
					lblInput3.setBounds(106, 360, 289, 15);
					mainPanel.add(lblInput3);
	
					txtInput3 = new JTextField();
					txtInput3.setBounds(430, 360, 247, 19);
					mainPanel.add(txtInput3);
					break;
			case 6: lblSubTitle.setText("Ricerca statistiche dei rapporti delle righe per gruppo spettrale");
			
					lblInput1 = new JLabel("Gruppo spettrale:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);

					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);

					lblInput2 = new JLabel("Flussi da rapportare:");
					lblInput2.setBounds(106, 285, 225, 15);
					mainPanel.add(lblInput2);

					txtInput2 = new JTextField();
					txtInput2.setBounds(325, 285, 150, 19);
					mainPanel.add(txtInput2);
					
					txtInput3 = new JTextField();
					txtInput3.setBounds(525, 285, 150, 19);
					mainPanel.add(txtInput3);
	
					lblInput3 = new JLabel("Apertura:");
					lblInput3.setBounds(106, 360, 200, 15);
					mainPanel.add(lblInput3);

					rdbtn3 = new JRadioButton("3x3");
					rdbtn3.setBounds(200, 360, 65, 15);
					mainPanel.add(rdbtn3);
					
					rdbtn5 = new JRadioButton("5x5");
					rdbtn5.setBounds(270, 360, 65, 15);
					mainPanel.add(rdbtn5);
					
					rdbtnC = new JRadioButton("c");
					rdbtnC.setBounds(340, 360, 65, 15);
					mainPanel.add(rdbtnC);
					
					rdbtnSame = new JRadioButton("Tutte o non necessaria");
					rdbtnSame.setBounds(410, 360, 200, 15);
					mainPanel.add(rdbtnSame);
					
					aperture = new ButtonGroup();
					aperture.add(rdbtn3);
					aperture.add(rdbtn5);
					aperture.add(rdbtnC);
					aperture.add(rdbtnSame);
					
					lblInput4 = new JLabel("Operazione richiesta:");
					lblInput4.setBounds(106, 435, 200, 15);
					mainPanel.add(lblInput4);
					
					rdbtnAvg = new JRadioButton("media");
					rdbtnAvg.setBounds(270, 435, 75, 15);
					mainPanel.add(rdbtnAvg);
					
					rdbtnMed = new JRadioButton("mediana");
					rdbtnMed.setBounds(345, 435, 90, 15);
					mainPanel.add(rdbtnMed);
					
					rdbtnDev = new JRadioButton("deviazione standar");
					rdbtnDev.setBounds(435, 435, 200, 15);
					mainPanel.add(rdbtnDev);
					
					rdbtnDevAvgAbs = new JRadioButton("deviazione standard assoluta");
					rdbtnDevAvgAbs.setBounds(270, 475, 275, 25);
					mainPanel.add(rdbtnDevAvgAbs);
					
					rdbtnAll = new JRadioButton("Tutte");
					rdbtnAll.setBounds(550, 475, 275, 25);
					mainPanel.add(rdbtnAll);
					
					operation = new ButtonGroup();
					operation.add(rdbtnAvg);
					operation.add(rdbtnMed);
					operation.add(rdbtnDev);
					operation.add(rdbtnDevAvgAbs);
					operation.add(rdbtnAll);
					break;
			case 7: lblSubTitle.setText("Ricerca rapporto flusso righa e flusso continuo");
			
					lblInput1 = new JLabel("Nome della galassia:");
					lblInput1.setBounds(106, 210, 306, 15);
					mainPanel.add(lblInput1);

					txtInput1 = new JTextField();
					txtInput1.setBounds(430, 210, 247, 19);
					mainPanel.add(txtInput1);

					lblInput2 = new JLabel("Ione di cui fare il rapporto:");
					lblInput2.setBounds(106, 285, 228, 15);
					mainPanel.add(lblInput2);

					txtInput2 = new JTextField();
					txtInput2.setBounds(430, 285, 247, 19);
					mainPanel.add(txtInput2);
					
					lblInput3 = new JLabel("Apertura del flusso riga:");
					lblInput3.setBounds(106, 360, 289, 15);
					mainPanel.add(lblInput3);
	
					rdbtn3 = new JRadioButton("3x3");
					rdbtn3.setBounds(400, 360, 65, 25);
					mainPanel.add(rdbtn3);
					
					rdbtn5 = new JRadioButton("5x5");
					rdbtn5.setBounds(470, 360, 65, 25);
					mainPanel.add(rdbtn5);
					
					rdbtnC = new JRadioButton("c");
					rdbtnC.setBounds(540, 360, 65, 25);
					mainPanel.add(rdbtnC);
					
					rdbtnSame = new JRadioButton("Stessa apertura del flusso continuo");
					rdbtnSame.setBounds(400, 395, 350, 25);
					mainPanel.add(rdbtnSame);
					
					aperture = new ButtonGroup();
					aperture.add(rdbtn3);
					aperture.add(rdbtn5);
					aperture.add(rdbtnC);
					aperture.add(rdbtnSame);
					break;
		}
		
		JButton btnQueryPerforme = new JButton("CERCA");
		btnQueryPerforme.setBounds(330, 550, 142, 25);
		mainPanel.add(btnQueryPerforme);
		
		JButton btnBack = new JButton("Torna al selettore di query");
		btnBack.setBounds(285, 600, 230, 25);
		mainPanel.add(btnBack);
		
		HomeActionListener = new HomeAL();
		LogoutActionListener = new LogoutAL();	
		BackActionListener = new BackAL();
		QueryActionListener = new QueryAL();
		
		btnQueryPerforme.addActionListener(QueryActionListener);
		btnBack.addActionListener(BackActionListener);
		btnLogout.addActionListener(LogoutActionListener);
		btnHome.addActionListener(HomeActionListener);
		
		mainPanel.updateUI();
	}
	
	private class QueryAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			QueryController cntr = QueryController.getInstance();
			switch (queryType) {
			case 1:
				try {
					String[] inputs = {txtInput1.getText().toLowerCase()};
					String[][] result = cntr.findGalaxy(inputs[0]);
					new ResultGUI(accountType, mainPanel, 1, inputs, result);
				} catch (GalaxyNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La galassia '" + txtInput1.getText().toLowerCase() + "' non esiste." , "Galassia inesistente", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case 2:
				try {
					String[] inputs = {txtInput1.getText(), txtInput2.getText(), txtInput3.getText(), txtInput4.getText()};
					String[][] result = cntr.galaxyInACircle(inputs);
					new ResultGUI(accountType, mainPanel, 2, inputs, result);
				} catch (PositionTableEmptyException e){
					JOptionPane.showMessageDialog(null, "Non ci sono posizioni salvate nel DB. Aggiungere galassie e poi riprovare." , "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;

				
			case 3:
				try {
					
					String range = null;
					if (rdbtnLess.isSelected()) {
						range = "<";
					}else if (rdbtnGreater.isSelected()) {
						range = ">";
					}else if (rdbtnEqual.isSelected()) {
						range = "=";
					} else {
						JOptionPane.showMessageDialog(null, "Selezionare un range" , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}				
					String[] inputs = {txtInput1.getText(), range, txtInput3.getText()};
					String[][] result = cntr.findRedShift(inputs);
					new ResultGUI(accountType, mainPanel, 3, inputs, result);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}	
			case 4:
				try {
					String[] inputs = {txtInput1.getText(), txtInput2.getText()};
					String[] fluxes = inputs[1].split("_");
					String[][] result = cntr.findFluxes(inputs[0], fluxes);
					new ResultGUI(accountType, mainPanel, 4, inputs, result);
				} catch (GalaxyNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La galassia '" + txtInput1.getText().toLowerCase() + "' non esiste." , "Galassia inesistente", JOptionPane.ERROR_MESSAGE);

				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case 5:
				try {
					String[] inputs = {txtInput1.getText().toLowerCase(), txtInput2.getText().toLowerCase(), txtInput3.getText().toLowerCase()};
					String[][] result = cntr.fluxRatio(inputs[0], inputs[1], inputs[2]);
					new ResultGUI(accountType, mainPanel, 5, inputs, result);
				}catch (GalaxyNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La galassia '" + txtInput1.getText().toLowerCase() + "' non esiste." , "Galassia inesistente", JOptionPane.ERROR_MESSAGE);
				}catch (FluxNotExistsException e) {
					switch (e.getType()) {
					case 1: JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput2.getText().toLowerCase() +  "' non è memorizzato per la galassia " + txtInput1.getText().toLowerCase(), "Errore", JOptionPane.ERROR_MESSAGE); break;
					case 2:	JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput3.getText().toLowerCase() +  "' non è memorizzato per la galassia " + txtInput1.getText().toLowerCase(), "Errore", JOptionPane.ERROR_MESSAGE); break;
					}					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case 6:
				try {
					String aper= null;
					if (rdbtn3.isSelected()) {
						aper = "3x3";
					}else if (rdbtn5.isSelected()) {
						aper = "5x5";
					}else if (rdbtnC.isSelected()) {
						aper = "c";
					}else if (rdbtnSame.isSelected()) {
						aper = null;
					} else {
						JOptionPane.showMessageDialog(null, "Selezionare un apertura" , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String oper = null;
					if (rdbtnAvg.isSelected()) {
						oper = "1";
					}else if (rdbtnMed.isSelected()) {
						oper = "2";
					}else if (rdbtnDev.isSelected()) {
						oper = "3";
					}else if (rdbtnDevAvgAbs.isSelected()) {
						oper = "4";
					}else if (rdbtnSame.isSelected()) {
						oper = "5";
					} else {
						JOptionPane.showMessageDialog(null, "Selezionare il tipo di operazione" , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String[] inputs = {txtInput1.getText().toLowerCase(), txtInput2.getText().toLowerCase(), txtInput3.getText().toLowerCase(), aper, oper};
					String[][] result = cntr.fluxStats(inputs[0], inputs[1], inputs[2], inputs[3], Integer.parseInt(inputs[4]));
					new ResultGUI(accountType, mainPanel, 6, inputs, result);
					
				} catch (ClassNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La classe spettrale '" + txtInput1.getText().toLowerCase() + "' non esisten nel database.", "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (FluxNotExistsException e) {
					switch (e.getType()) {
					case 1: JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput2.getText().toLowerCase() +  "' non esiste nel database ", "Errore", JOptionPane.ERROR_MESSAGE); break;
					case 2:	JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput3.getText().toLowerCase() +  "' non esiste nel database ", "Errore", JOptionPane.ERROR_MESSAGE); break;
					case 3:JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput2.getText().toLowerCase() +  "' non esiste per nessuna galassia della classe inserita. Impossibile dunque fare statistiche.", "Errore", JOptionPane.ERROR_MESSAGE); break;
					case 4:JOptionPane.showMessageDialog(null, "Il flusso '" + txtInput3.getText().toLowerCase() +  "' non esiste per nessuna galassia della classe inserita. Impossibile dunque fare statistiche.", "Errore", JOptionPane.ERROR_MESSAGE); break;
					}	
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case 7:
				try {
					String aper= null;
					if (rdbtn3.isSelected()) {
						aper = "3x3";
					}else if (rdbtn5.isSelected()) {
						aper = "5x5";
					}else if (rdbtnC.isSelected()) {
						aper = "c";
					}else if (rdbtnSame.isSelected()) {
						aper = null;
					} else {
						JOptionPane.showMessageDialog(null, "Selezionare un apertura" , "Errore", JOptionPane.ERROR_MESSAGE);
						return;
					}
					String[] inputs = {txtInput1.getText().toLowerCase(), txtInput2.getText().toLowerCase(), aper};
					String[][] result = cntr.fluxContRowRatio(inputs[0], inputs[1], inputs[2]);
					new ResultGUI(accountType, mainPanel, 7, inputs, result);
				}catch (GalaxyNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La galassia '" + txtInput1.getText().toLowerCase() + "' non esiste." , "Galassia inesistente", JOptionPane.ERROR_MESSAGE);
				}catch (FluxNotExistsException e) {
					switch (e.getType()) {
					case 1: JOptionPane.showMessageDialog(null, "Il flusso riga '" + txtInput2.getText().toLowerCase() +  "' con apertura selezionata non è memorizzato per la galassia " + txtInput1.getText().toLowerCase(), "Errore", JOptionPane.ERROR_MESSAGE); break;
					case 2:	JOptionPane.showMessageDialog(null, "Il flusso continuo '" + txtInput2.getText().toLowerCase() +  "' non è memorizzato per la galassia " + txtInput1.getText().toLowerCase(), "Errore", JOptionPane.ERROR_MESSAGE); break;
					}		
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private class LogoutAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new LoginGUI(mainPanel);
		}
	}
	
	private class BackAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QuerySelectorGUI(accountType, mainPanel);
		}
	}
	
	private class HomeAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new HomeGUI(accountType, mainPanel);
		}
	}
}
