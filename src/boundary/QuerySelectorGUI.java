package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class QuerySelectorGUI {
	private int accountType;
	public JPanel mainPanel;

	JLabel lblSelectedQuery;
	
	private ByNameAL ByNameActionListener;
	private ByDistanceAL ByDistanceActionListener;
	private LogoutAL LogoutActionListener;
	private HomeAL HomeActionListener;	
	private ByPhysicalAL ByPhysicalActionListener;
	private ByRowAL ByRowActionListener;
	private ByRatioAL ByRatioActionListener;
	private ByGroupAL ByGroupActionListener;
	private ByGropuApAL ByGropuApActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public QuerySelectorGUI(int accountType, JPanel mainPanel) {
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
		
		JLabel lblTitle = new JLabel("Fai una ricerca");
		lblTitle.setBounds(274, 77, 267, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JLabel lblSubTitle = new JLabel("Seleziona il tipo di ricerca da effettuare");
		lblSubTitle.setBounds(261, 132, 314, 25);
		mainPanel.add(lblSubTitle);
		
		JButton btnByName = new JButton("<html>1) Cerca galassia per <br> nome<html>");
		btnByName.setBounds(138, 200, 220, 40);
		mainPanel.add(btnByName);
			
		JButton btnByDistance = new JButton("<html>2) Cerca galassie all'<br> interno di un raggio<html>");
		btnByDistance.setBounds(435, 200, 220, 40);
		mainPanel.add(btnByDistance);
			
		JButton btnByPhysical = new JButton("<html>3) Cerca per <br> caratteristiche fisiche<html>");
		btnByPhysical.setBounds(138, 300, 220, 40);
		mainPanel.add(btnByPhysical);
		
		JButton btnByRow = new JButton("<html>4) Cerca valori linee <br> spettrali<html>");
		btnByRow.setBounds(435, 300, 220, 40);
		mainPanel.add(btnByRow);
		
		JButton btnByRatio = new JButton("<html>5) Cerca valori dei <br> rapporti delle righe <br> spettrali<html>");
		btnByRatio.setBounds(138, 400, 220, 76);
		mainPanel.add(btnByRatio);
		
		JButton btnByGroup = new JButton("<html>6) Statistiche valori dei <br> rapporti delle righe <br> per gruppo spettrale<html>");
		btnByGroup.setBounds(138, 500, 220, 76);
		mainPanel.add(btnByGroup);
			
		JButton btnByGropuAp = new JButton("<html>7) Cerca rapporto tra <br> flusso riga e continuo<html>");
		btnByGropuAp.setBounds(435, 425, 220, 76);
		mainPanel.add(btnByGropuAp);	
		
		HomeActionListener = new HomeAL();
		LogoutActionListener = new LogoutAL();
		ByNameActionListener = new ByNameAL();
		ByDistanceActionListener = new ByDistanceAL();
		ByPhysicalActionListener = new ByPhysicalAL();
		ByRowActionListener = new ByRowAL();
		ByRatioActionListener = new ByRatioAL();
		ByGroupActionListener = new ByGroupAL();
		ByGropuApActionListener = new ByGropuApAL();
		
		btnByDistance.addActionListener(ByDistanceActionListener);
		btnByName.addActionListener(ByNameActionListener);
		btnLogout.addActionListener(LogoutActionListener);
		btnHome.addActionListener(HomeActionListener);
		btnByPhysical.addActionListener(ByPhysicalActionListener);
		btnByRow.addActionListener(ByRowActionListener);
		btnByRatio.addActionListener(ByRatioActionListener);
		btnByGroup.addActionListener(ByGroupActionListener);
		btnByGropuAp.addActionListener(ByGropuApActionListener);
		
		mainPanel.updateUI();
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
	
	private class ByNameAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 1);
		}
	}
	
	private class ByDistanceAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 2);
		}
	}
	
	private class ByPhysicalAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 3);
		}
	}
	
	private class ByRowAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 4);
		}
	}
	
	private class ByRatioAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 5);
		}
	}
	
	private class ByGroupAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 6);
		}
	}
	
	private class ByGropuApAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QueryGUI(accountType, mainPanel, 7);
		}
	}
}