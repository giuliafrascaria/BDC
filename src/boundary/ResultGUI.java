package boundary;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultGUI{
	private int accountType;
	public JPanel mainPanel;
	
	private LogoutAL LogoutActionListener;
	private BackAL BackActionListener;
	private HomeAL HomeActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public ResultGUI(int accountType, JPanel mainPanel, int queryType, String[] inputs, String[][] outputs) {
		setMainPanel(mainPanel);
		mainPanel.removeAll();
		this.accountType = accountType;
		
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
		
		JLabel lblTitle = new JLabel("Risultati");
		lblTitle.setBounds(326, 70, 190, 30);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		TextArea txtResult = new TextArea();
		txtResult.setBounds(10, 150, 780, 550);
		txtResult.setEditable(false);
		mainPanel.add(txtResult);
		
		JLabel lblSubTitle = new JLabel("");
		lblSubTitle.setBounds(138, 100, 510, 20);
		mainPanel.add(lblSubTitle);
		
		JLabel lblSubTitle2 = new JLabel("");
		lblSubTitle2.setBounds(138, 122, 510, 20);
		mainPanel.add(lblSubTitle2);
		
		JButton btnBack = new JButton("Torna al selettore di query");
		btnBack.setBounds(285, 715, 230, 25);
		mainPanel.add(btnBack);
		
		switch (queryType) {
		case 1: lblSubTitle.setText("Ricerca per nome: " + inputs[0]);
				txtResult.setText("asc(ore_min_sec)_decl(segno_gradi_min_sec)_distanza_redshift_limit_NeV14.3_limit_NeV24.3_limit_OIV25.9"
						+ "_met_errMet \n\n");
				for (int i=1; i<outputs[0].length; i++) {
					if (i == 1) {
						txtResult.setText(txtResult.getText() + outputs[0][i]);
					} else {
						txtResult.setText(txtResult.getText() + "___" + outputs[0][i]);
					}
				}
				txtResult.setText(txtResult.getText() + "\n NOTE: '/' significa valore ingoto.");
				break;
		case 2: lblSubTitle.setText("Ricerca entro raggio: " + inputs[1] + ", massimo " + inputs[0] + " risultati.");
				lblSubTitle2.setText("Centro in asc:" + inputs[2] + " e decl:" + inputs[3]);
				txtResult.setText("Nome galassia\tdistanza dal centro.\n\n");
				for (int i=0; i<outputs[0].length; i++) {
					String foo = String.format("%-20s \t %s", outputs[0][i], outputs[1][i]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				break;
		case 3: lblSubTitle.setText("Ricerca per caratteristiche fisiche: prime " + inputs[2]+ " galassie con redshift " + inputs[1] + " " + inputs[0]);
				txtResult.setText("Nome galassia\tredshift\n\n");
				for (int i=0; i<outputs[0].length; i++) {
					
			        String foo = String.format("%-20s \t %s", outputs[0][i], outputs[1][i]);				         
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				break;
		case 4: lblSubTitle.setText("Ricerca linee spettrali: " + inputs[1] + "\n");
				lblSubTitle2.setText("Per la galassia: " + inputs[0]);
				if (outputs[0].length > 0) {
					txtResult.setText("Non sono stati trovati i flussi: ");
					for (String f : outputs[0]) {
						txtResult.setText(txtResult.getText() + f + " ");
					}
				}
				txtResult.setText(txtResult.getText() + "\n\n Flusso\t\tvalore\tupperLimit o errore\tapertura(eventuale per identificare)\n\n");
				for (int i = 0; i<outputs[1].length; i++) {
					String limit;
					if (outputs[2][i].equals("0")) {
						limit = "upperLimit";
					} else {
						limit = outputs[2][i];
					}
					String foo = String.format("%-20s \t %s \t %s", outputs[3][i] + " (continuo)", outputs[1][i], limit);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				for (int i = 0; i<outputs[4].length; i++) {
					String limit;
					if (outputs[5][i].equals("0")) {
						limit = "upperLimit";
					} else {
						limit = outputs[5][i];
					}
					String foo = String.format("%-20s \t %s \t %s \t\t %s", outputs[7][i] + " (riga)", outputs[4][i], limit, outputs[6][i]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				break;
		case 5: lblSubTitle.setText("Ricerca dei rapporti righe spettrali della galassia: " + inputs[0]);
				lblSubTitle2.setText("Flusso numeratore: " + inputs[1] + " Flusso denominatore: " + inputs[2]);
				txtResult.setText("Valore del rapporto\tinformazioni sul valore.\n\n");
				String fo = String.format("%-20s \t %s", outputs[0][0], outputs[0][1]);
				txtResult.setText(txtResult.getText() + fo +"\n");
				break;
		case 6: lblSubTitle.setText("Ricerca statistiche del gruppo spettrale: " + inputs[0]);
				lblSubTitle2.setText("Del flusso: " + inputs[1] + " Flusso denominatore: " + inputs[2]);
				int oper = Integer.parseInt(inputs[4]);
				txtResult.setText("Operazione\t\trisultato.\n\n");
				if (oper == 1 || oper == 5) {
					//average
					String foo = String.format("%-20s \t %s", "Media", outputs[0][0]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				if (oper == 2 || oper == 5) {
					//median
					String foo = String.format("%-20s \t %s", "Mediana", outputs[0][1]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				if (oper == 3 || oper == 5) {
					//standard deviation
					String foo = String.format("%-20s \t %s", "Deviazione standard", outputs[0][2]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				if (oper == 4 || oper == 5) {
					//absolute average deviation
					String foo = String.format("%-20s \t %s", "Deviazione media assoluta", outputs[0][3]);
					txtResult.setText(txtResult.getText() + foo +"\n");
				}
				break;
		case 7: String aper = inputs[2];
				if (inputs[2] == null) {
					aper = "del continuo";
				}
				lblSubTitle.setText("Ricerca rapporto flusso riga (apertura " +  aper + ") e continuo: " + inputs[1]);
				lblSubTitle2.setText("Per la galassia:" + inputs[0]);
				txtResult.setText("Valore del rapporto(continuo al denominatore)\tinformazioni sul valore.\n\n");
				String foo = String.format("%-20s \t\t\t %s", outputs[0][0] , outputs[0][1]);
				txtResult.setText(txtResult.getText() + foo +"\n");
				break;
	}
		
		LogoutActionListener = new LogoutAL();
		HomeActionListener = new HomeAL();
		BackActionListener = new BackAL();
		
		btnBack.addActionListener(BackActionListener);
		btnHome.addActionListener(HomeActionListener);
		btnLogout.addActionListener(LogoutActionListener);
		
		mainPanel.updateUI();
	}
	
	private class BackAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QuerySelectorGUI(accountType, mainPanel);
		}
	}
	
	private class LogoutAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new LoginGUI(mainPanel);
		}
	}
	
	private class HomeAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new HomeGUI(accountType, mainPanel);
		}
	}
}
