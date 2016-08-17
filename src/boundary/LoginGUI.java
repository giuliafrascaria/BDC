package boundary;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.LoginController;
import entity.User;
import exceptions.UserDoNotExistsException;
import exceptions.WrongPasswordException;

public class LoginGUI extends JFrame{

	
	private static final long serialVersionUID = 7391476762904424961L;

	
	public static JFrame bound;
	public static JPanel mainPanel = new JPanel();
	public JTextField txtUserID;
	public JPasswordField password;

	private LoginAL LoginActionListener;
	private ResetAL ResetActionListener;
	
	private void setMainPanel(JPanel mainPanel) {
		LoginGUI.mainPanel = mainPanel;
	}
	
	private void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	public LoginGUI() {
		bound = this;
		bound.getContentPane().setLayout(null);
		final int BASEBOUND = 800;
		final int HEIGHTBOUND = 800;
		setSize(BASEBOUND, HEIGHTBOUND);
		Dimension dim = getToolkit().getScreenSize();
        setLocation(dim.width/2 - getWidth()/2, dim.height/2 - getHeight()/2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        
        mainPanel.setSize(this.getWidth(), this.getHeight());
		getContentPane().add(mainPanel);
		mainPanel.setLayout(null);

		setTitle("Galaxies explorer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 800, 800);
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setBounds(331, 55, 129, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JLabel lblUserID = new JLabel("User-ID:");
		lblUserID.setBounds(241, 146, 96, 25);
		lblUserID.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
		mainPanel.add(lblUserID);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(366, 148, 232, 25);
		mainPanel.add(txtUserID);
		txtUserID.setColumns(10);
				
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(208, 213, 129, 25);
		lblPassword.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
		mainPanel.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(366, 215, 232, 25);
		mainPanel.add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(299, 292, 186, 25);
		mainPanel.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(351, 355, 85, 25);
		mainPanel.add(btnReset);
		
		LoginActionListener = new LoginAL();
		btnLogin.addActionListener(LoginActionListener);
		
		ResetActionListener = new ResetAL();
		btnReset.addActionListener(ResetActionListener);
		
		centreWindow();
		this.setVisible(true);
		/*String[] pr = {"prova"};
		String[] cr = {"casa"};
		String[][] a = {pr, cr};
		new ResultGUI(1, mainPanel, 1, pr, a);*/
	}
	
	public LoginGUI(JPanel mainPanel) {
		setMainPanel(mainPanel);
		mainPanel.removeAll();
		//mainPanel.updateUI();
		
		JLabel lblTitle = new JLabel("Login");
		lblTitle.setBounds(331, 55, 129, 32);
		lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
		mainPanel.add(lblTitle);
		
		JLabel lblUserID = new JLabel("User-ID:");
		lblUserID.setBounds(241, 146, 96, 25);
		lblUserID.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
		mainPanel.add(lblUserID);
		
		txtUserID = new JTextField();
		txtUserID.setBounds(366, 148, 232, 25);
		mainPanel.add(txtUserID);
		txtUserID.setColumns(10);
				
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(208, 213, 129, 25);
		lblPassword.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
		mainPanel.add(lblPassword);
		
		password = new JPasswordField();
		password.setBounds(366, 215, 232, 25);
		mainPanel.add(password);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(299, 292, 186, 25);
		mainPanel.add(btnLogin);
		
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(351, 355, 85, 25);
		mainPanel.add(btnReset);
		
		LoginActionListener = new LoginAL();
		btnLogin.addActionListener(LoginActionListener);
		
		ResetActionListener = new ResetAL();
		btnReset.addActionListener(ResetActionListener);
		
		mainPanel.updateUI();
	}
	
	private class ResetAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			txtUserID.setText(null);
			password.setText(null);
		}
	}

	private class LoginAL implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			LoginController controller = LoginController.getInstance();
			try {
				User user = controller.login(txtUserID.getText(), String.valueOf(password.getPassword()));
				new HomeGUI(user.getAccountType(), mainPanel);
			} catch (WrongPasswordException e) {
				JOptionPane.showMessageDialog(null, "Password errata per lo user-ID inserito" , "Password errata", JOptionPane.ERROR_MESSAGE);
			} catch (UserDoNotExistsException e) {
				JOptionPane.showMessageDialog(null, "Non esiste alcun utente con lo user-ID specificato." , "User-ID non esistente", JOptionPane.ERROR_MESSAGE);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Si è verificato un errore interno, per favore riprova più tardi o contatta l'assistenza." , "Errore interno", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
