package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.UploadController;

public class HomeGUI {
	
	private int accountType;
	public JPanel mainPanel;
	
	private JTextField txtFilePath;
	private JButton btnBrowserFile;
	private JFileChooser chooser;
	public JRadioButton rdbtnGalaxies;
	public JRadioButton rdbtnRowFluxPacs;
	public JRadioButton rdbtnContinuosFluxPacs;
	public JRadioButton rdbtnRowFluxSpitzer;
	public ButtonGroup FileType;

	private SearchFileAL SearchFileActionListener;
	private RegistrationtAL RegistrationtActionListener;
	private LogoutAL LogoutActionListener;
	private LoadFileAL LoadFileActionListener;
	private QueryAL QueryActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	
	public HomeGUI(int accountType, JPanel mainPanel) {
		setMainPanel(mainPanel);
		mainPanel.removeAll();
		//mainPanel.updateUI();
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
		
		JLabel lblTitle = new JLabel("Benvenuti");
		lblTitle.setBounds(326, 85, 190, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JButton btnSearch = new JButton("Fai una ricerca");
		btnSearch.setBounds(307, 157, 220, 25);
		mainPanel.add(btnSearch);
		
		LogoutActionListener = new LogoutAL();
		QueryActionListener = new QueryAL();
		btnSearch.addActionListener(QueryActionListener);
		btnLogout.addActionListener(LogoutActionListener);
		
		if (accountType == 1) {
			JButton btnRegisterNew = new JButton("Registra un nuovo utente");
			btnRegisterNew.setBounds(307, 252, 220, 25);
			mainPanel.add(btnRegisterNew);
			
			JLabel lblLoadFile = new JLabel("Carica un nuovo file");
			lblLoadFile.setBounds(338, 339, 178, 32);
			lblLoadFile.setFont(new Font("Serif", Font.BOLD, 15));
			mainPanel.add(lblLoadFile);
			
			JLabel lblChoose = new JLabel("Scegli un file .csv");
			lblChoose.setBounds(170, 394, 129, 25);
			mainPanel.add(lblChoose);
			
			txtFilePath = new JTextField();
			txtFilePath.setBounds(300, 394, 288, 25);
			mainPanel.add(txtFilePath);
			txtFilePath.setColumns(10);
			
			btnBrowserFile = new JButton("Sfoglia");		
			btnBrowserFile.setBackground(Color.LIGHT_GRAY);
			btnBrowserFile.setBounds(600, 395, 89, 23);
			mainPanel.add(btnBrowserFile);
			
			JLabel lblFileType = new JLabel("Seleziona il tipo di file immesso:");
			lblFileType.setBounds(39, 528, 241, 25);
			mainPanel.add(lblFileType);
			
			rdbtnGalaxies = new JRadioButton("di galassie;"); //File type 1
			rdbtnGalaxies.setBounds(294, 465, 145, 25);
			mainPanel.add(rdbtnGalaxies);
			
			rdbtnRowFluxPacs = new JRadioButton("flussi righe di Hershel/PACS;"); //File type 2
			rdbtnRowFluxPacs.setBounds(294, 505, 451, 25);
			mainPanel.add(rdbtnRowFluxPacs);
			
			rdbtnContinuosFluxPacs = new JRadioButton("flussi continui di Hersel/PACS;"); //File type 3
			rdbtnContinuosFluxPacs.setBounds(294, 545, 433, 25);
			mainPanel.add(rdbtnContinuosFluxPacs);
			
			rdbtnRowFluxSpitzer = new JRadioButton("flussi riga di Spitzer;"); //File type 4
			rdbtnRowFluxSpitzer.setBounds(294, 585, 251, 25);
			mainPanel.add(rdbtnRowFluxSpitzer);
			
			FileType = new ButtonGroup();
			FileType.add(rdbtnGalaxies);
			FileType.add(rdbtnRowFluxPacs);
			FileType.add(rdbtnContinuosFluxPacs);
			FileType.add(rdbtnRowFluxSpitzer);
			
			JButton btnUpFile = new JButton("Carica File");
			btnUpFile.setBounds(294, 673, 203, 25);
			mainPanel.add(btnUpFile);
		
			RegistrationtActionListener = new RegistrationtAL();
			SearchFileActionListener = new SearchFileAL();
			LoadFileActionListener = new LoadFileAL();
			
			btnRegisterNew.addActionListener(RegistrationtActionListener);
			btnBrowserFile.addActionListener(SearchFileActionListener);
			btnUpFile.addActionListener(LoadFileActionListener);
		}
		
		mainPanel.updateUI();
	}
	
	private class SearchFileAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			chooser = new JFileChooser();
		    chooser.setDialogTitle("Scegli il file csv");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);
	        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Comma-Separated Values .csv", "csv"));
		   
		    String pathfolder = "";
		    
		    if (chooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
		    	File path = chooser.getSelectedFile();
		    	pathfolder += path;
		    	txtFilePath.setText(pathfolder);
		    	if(!pathfolder.endsWith(".csv")){
		    		JOptionPane.showMessageDialog(null, "Formato file non valido!", "Errore", JOptionPane.ERROR_MESSAGE);
		    		txtFilePath.setText("");
		    	}
		    }
		}
	}
	
	private class LoadFileAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			File f = new File(txtFilePath.getText());
			if(txtFilePath.getText().equals("") || !txtFilePath.getText().endsWith(".csv")){
				JOptionPane.showMessageDialog(null, "Seleziona un file CSV", "Errore", JOptionPane.ERROR_MESSAGE);
			} else if (!f.exists() || f.isDirectory()) {
				JOptionPane.showMessageDialog(null, "Il file immesso non esiste", "Errore", JOptionPane.ERROR_MESSAGE);
			} else if(!(rdbtnGalaxies.isSelected() || rdbtnRowFluxPacs.isSelected() || rdbtnContinuosFluxPacs.isSelected() || 
					rdbtnRowFluxSpitzer.isSelected())){
				JOptionPane.showMessageDialog(null, "Seleziona il tipo di File inserito", "Errore", JOptionPane.ERROR_MESSAGE);
			} else {
				UploadController controller = UploadController.getInstance();
				controller.setFilePath(txtFilePath.getText());
				if(rdbtnGalaxies.isSelected()){
					controller.setFileType(1);
				}
				if(rdbtnRowFluxPacs.isSelected()){
					controller.setFileType(2);
				}
				if(rdbtnContinuosFluxPacs.isSelected()){
					controller.setFileType(3);
				}
				if(rdbtnRowFluxSpitzer.isSelected()){
					controller.setFileType(4);
				}
				controller.uploadFile();
			}
		}
	}
	
	private class QueryAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new QuerySelectorGUI(accountType, mainPanel);
		}
	}
	
	private class LogoutAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new LoginGUI(mainPanel);
		}
	}

	private class RegistrationtAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			new RegistrationGUI(mainPanel, accountType);
		}
	}
}
