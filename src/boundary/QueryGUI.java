package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.QueryController;
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
					lblInput1 = new JLabel("nome della galassia (Key Sensitive):");
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
					break;
			case 4: lblSubTitle.setText("Ricerca linee spettrali");
					break;
			case 5: lblSubTitle.setText("Ricerca dei rapporti righe spettrali");
					break;
			case 6: lblSubTitle.setText("Ricerca dei rapporto flusso riga e continuo");
					break;
			case 7: lblSubTitle.setText("Ricerca dei rapporti delle righe per gruppo spettrale");
					break;
			case 8: lblSubTitle.setText("Ricerca dei rapporti delle righe per gruppo spettrale per un apertura");
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
					String[] inputs = {txtInput1.getText()};
					String[][] result = cntr.findGalaxy(txtInput1.getText());
					new ResultGUI(accountType, mainPanel, 1, inputs, result);
				} catch (GalaxyNotExistsException e) {
					JOptionPane.showMessageDialog(null, "La galassia '" + e.getName() + "' non esiste. \nRicorda di fare attenzione alle maiuscole e minuscole." , "Galassia inesistente", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
			case 2:
				String[] inputs = {txtInput1.getText(), txtInput2.getText(), txtInput3.getText(), txtInput4.getText()};
				try {
					String[][] result = cntr.galaxyInACircle(inputs);
					new ResultGUI(accountType, mainPanel, 2, inputs, result);
				} catch (PositionTableEmptyException e){
					JOptionPane.showMessageDialog(null, "Non ci sono posizioni salvate nel DB. Aggiungere galassie e poi riprovare." , "Errore", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, riprovare più tardi" , "Errore", JOptionPane.ERROR_MESSAGE);
				}
				break;
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
