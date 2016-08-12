package boundary;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WaitingGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private void centreWindow() {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    this.setLocation(x, y);
	}
	
	 public void removeMinMaxClose(Component comp)
	  {
	    if(comp instanceof AbstractButton)
	    {
	      comp.getParent().remove(comp);
	    }
	    if (comp instanceof Container)
	    {
	      Component[] comps = ((Container)comp).getComponents();
	      for(int x = 0, y = comps.length; x < y; x++)
	      {
	        removeMinMaxClose(comps[x]);
	      }
	    }
	  }
	
	public WaitingGUI(){
		
		setTitle("Caricamento file: attendere");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize( 320, 250);
		
		addWindowListener(new WindowAdapter() {
			 
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	setAlwaysOnTop(false);
	        	if (JOptionPane.showConfirmDialog(null, "Il caricamento del file verrà interrotto e l'applicazione verrà chiusa.\n"
	        		    + "Sei sicuro di voler uscire?\n", "WARNING",
	        	        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	        	    System.exit(0);
	        	}
	        	else{
	        		setAlwaysOnTop(true);
	        	}
	        	
	        }
	    });
		
		centreWindow();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblBackground = new JLabel();	
		lblBackground.setIcon(new ImageIcon("./resource/ajax-document-loader.gif"));
		lblBackground.setBounds(0, 0, 320, 224);
		lblBackground.setFocusable(true);
		contentPane.add(lblBackground);
		
		this.setVisible(true);
		
	}
	

}
