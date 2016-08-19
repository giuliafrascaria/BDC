package boundary;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultGUI {
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
						+ "_met_errMet \n NOTE: '/' significa valore ingoto. \n\n");
				for (int i=1; i<outputs[0].length; i++) {
					txtResult.setText(txtResult.getText() + "__" +outputs[0][i]);
				}
				break;
		case 2: lblSubTitle.setText("Ricerca entro raggio: " + inputs[1] + ", massimo " + inputs[0] + " risultati.");
				lblSubTitle2.setText("Centro in asc:" + inputs[2] + " e decl:" + inputs[3]);
				txtResult.setText("Nome galassia____distanza dal centro.\n\n");
				for (int i=0; i<outputs[0].length; i++) {
					txtResult.setText(txtResult.getText() + outputs[0][i] + "____" + outputs[1][i] +"\n");
				}
				break;
		case 3: lblSubTitle.setText("Ricerca per caratteristiche fisiche: ");
				break;
		case 4: lblSubTitle.setText("Ricerca linee spettrali: ");
				break;
		case 5: lblSubTitle.setText("Ricerca dei rapporti righe spettrali: ");
				break;
		case 6: lblSubTitle.setText("Ricerca dei rapporto flusso riga e continuo: ");
				break;
		case 7: lblSubTitle.setText("Ricerca dei rapporti delle righe per gruppo spettrale: ");
				break;
		case 8: lblSubTitle.setText("Ricerca dei rapporti delle righe per gruppo spettrale per un apertura: ");
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
