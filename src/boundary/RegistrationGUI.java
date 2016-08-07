package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.RegistrationController;
import exceptions.EmailMismatchException;
import exceptions.IncopleteFormException;
import exceptions.InvalidMailException;
import exceptions.PasswordMismatchException;
import exceptions.UserAlreadyRegistredException;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPasswordField;

public class RegistrationGUI {
	
	private JPanel mainPanel;
	private int accountType;
	
	public JTextField txtName;
	public JTextField txtLastName;
	public JTextField txtUserID;
	public JTextField txtEmail;
	public JTextField txtEmailCopy;
	public JPasswordField password;
	public JPasswordField passwordCopy;
	public ButtonGroup newAccountType;
	public JRadioButton rdbtnAdmin;
	public JRadioButton rdbtnNormal;
	
	private ResetAL ResetActionListener;
	private RegisterAL RegisterActionListener;
	private LogoutAL LogoutActionListener;
	private HomeAL HomeActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}
	

	public RegistrationGUI(JPanel mainPanel, int accountType) {
		setMainPanel(mainPanel);
		mainPanel.removeAll();
		//mainPanel.updateUI();
		this.accountType = accountType;
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBounds(180, 20, 490, 32);
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
		
		JLabel lblTitle = new JLabel("Form di registrazione");
		lblTitle.setBounds(239, 96, 319, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JLabel lblFirstName = new JLabel("Nome:");
		lblFirstName.setBounds(100, 170, 90, 25);
		mainPanel.add(lblFirstName);
		
		txtName = new JTextField();
		txtName.setBounds(195, 170, 140, 25);
		mainPanel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblLastName = new JLabel("Cognome:");
		lblLastName.setBounds(425, 170, 90, 25);
		mainPanel.add(lblLastName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(520, 170, 140, 25);
		mainPanel.add(txtLastName);
		txtLastName.setColumns(10);
		
		JLabel lblUserID = new JLabel("User-ID:");
		lblUserID.setBounds(80, 240, 70, 25);
		mainPanel.add(lblUserID);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(145, 240, 172, 25);
		mainPanel.add(txtUserID);
		txtUserID.setColumns(10);
		
		JLabel lblAccountType = new JLabel("Tipo di account:");
		lblAccountType.setBounds(450, 240, 105, 25);
		mainPanel.add(lblAccountType);
		
		rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(560, 225, 85, 25);
		mainPanel.add(rdbtnAdmin);
		
		rdbtnNormal = new JRadioButton("Normale");
		rdbtnNormal.setBounds(560, 255, 85, 25);
		mainPanel.add(rdbtnNormal);
		
		newAccountType = new ButtonGroup();
		newAccountType.add(rdbtnAdmin);
		newAccountType.add(rdbtnNormal);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setBounds(60, 310, 45, 25);
		mainPanel.add(lblMail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(110, 310, 185, 25);
		mainPanel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblMailCopy = new JLabel("Ripeti la mail:");
		lblMailCopy.setBounds(380, 310, 130, 25);
		mainPanel.add(lblMailCopy);
		
		txtEmailCopy = new JTextField();
		txtEmailCopy.setBounds(515, 310, 185, 25);
		mainPanel.add(txtEmailCopy);
		txtEmailCopy.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(60, 380, 80, 25);
		mainPanel.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(145, 380, 150, 25);
		mainPanel.add(password);
		
		JLabel lblPasswordCopy = new JLabel("Ripeti la password:");
		lblPasswordCopy.setBounds(380, 380, 160, 25);
		mainPanel.add(lblPasswordCopy);
		
		passwordCopy = new JPasswordField();
		passwordCopy.setBounds(545, 380, 155, 25);
		mainPanel.add(passwordCopy);
		
		JButton btnRegisterMe = new JButton("Registra un nuovo utente");
		btnRegisterMe.setBounds(290, 450, 220, 25);
		mainPanel.add(btnRegisterMe);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(356, 480, 85, 25);
		mainPanel.add(btnReset);
		
		ResetActionListener	= new ResetAL();
		RegisterActionListener = new RegisterAL();
		HomeActionListener = new HomeAL();
		LogoutActionListener = new LogoutAL();
		
		btnLogout.addActionListener(LogoutActionListener);
		btnHome.addActionListener(HomeActionListener);
		btnReset.addActionListener(ResetActionListener);
		btnRegisterMe.addActionListener(RegisterActionListener);
		
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
	
	private class ResetAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			txtName.setText(null);
			txtLastName.setText(null);
			txtEmail.setText(null);
			txtEmailCopy.setText(null);
			password.setText(null);
			passwordCopy.setText(null);
			newAccountType.clearSelection();
		}
	}
	
	private class RegisterAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				String mail = txtEmail.getText();
				if (txtName.getText().equals("") || txtUserID.getText().equals("") || txtLastName.getText().equals("") || !(rdbtnAdmin.isSelected() || rdbtnNormal.isSelected()) || 
					mail.equals("") || String.valueOf(password.getPassword()).equals("")){
					throw new IncopleteFormException();
				}
				
				if (!mail.equals(txtEmailCopy.getText())){ throw new EmailMismatchException(); }
				
				if (mail.indexOf("@") == -1 || mail.indexOf("@") < 1) { throw new InvalidMailException(); }
				
				if (!String.valueOf(password.getPassword()).equals(String.valueOf(passwordCopy.getPassword()))) { throw new PasswordMismatchException(); }
								
				int account ;
				if (rdbtnAdmin.isSelected()) {
					account = 1;
				} else {
					account = 0;
				}
				
				RegistrationController controller = RegistrationController.getInstance();
				controller.registerUser(txtName.getText(), txtLastName.getText(), txtUserID.getText(), mail, String.valueOf(password.getPassword()), account);
				txtName.setText(null);
				txtLastName.setText(null);
				txtUserID.setText(null);
				txtEmail.setText(null);
				txtEmailCopy.setText(null);
				password.setText(null);
				passwordCopy.setText(null);
				newAccountType.clearSelection();
				JOptionPane.showMessageDialog(null, "Il nuovo utente è stato salvato correttamente." , "Registrazione completata", JOptionPane.INFORMATION_MESSAGE);
			} catch (EmailMismatchException e) {
				JOptionPane.showMessageDialog(null, "Le mail inserite non sono uguali, per favore fai attenzione." , "Email non combacianti", JOptionPane.ERROR_MESSAGE);
			} catch (InvalidMailException e) {
				JOptionPane.showMessageDialog(null, "La mail inserita non è valita, manca il simbolo '@'." , "Email non valida", JOptionPane.ERROR_MESSAGE);
			} catch (PasswordMismatchException e) {
				JOptionPane.showMessageDialog(null, "Le password inserite non sono uguali, per favore fai attenzione." , "Password non combacianti", JOptionPane.ERROR_MESSAGE);
			} catch (IncopleteFormException e) {
				JOptionPane.showMessageDialog(null, "Tutti i campi devono essere compilati." , "Form Incopleto", JOptionPane.ERROR_MESSAGE);
			} catch (UserAlreadyRegistredException e) {
				JOptionPane.showMessageDialog(null, "Esiste già un utente con lo user-ID inserito, per favore prova con un altro." , "Utente già registrato", JOptionPane.ERROR_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, per favore riprova più tardi o contatta l'assistenza." , "Errore interno", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
