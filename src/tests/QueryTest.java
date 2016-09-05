package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import controller.LoginController;
import controller.QueryController;
import controller.RegistrationController;
import entity.User;
import exceptions.GalaxyNotExistsException;
import exceptions.PositionTableEmptyException;

public class QueryTest {
	private QueryController cntr = QueryController.getInstance();
	private String userID;
	private String password;
	
	@Test
	public void testRegistrationLogin() {
		RegistrationController controller = RegistrationController.getInstance();
		LoginController controller2 = LoginController.getInstance();
		try {
			controller.registerUser("mario", "de angelis", "mariodea", "prova@prova", "password", 1);
			controller.registerUser("giulia", "frascaria", "giuliafra", "prova1@prova1", "password", 0);
			userID = "mariodea";
			password= "password";
			User user = controller2.login(userID, password);
			Assert.assertEquals(userID, user.getUserID());
			Assert.assertEquals(password, user.getPassword());
			Assert.assertEquals(1, user.getAccountType());
			userID = "giuliafra";
			user = controller2.login(userID, password);
			Assert.assertEquals(userID, user.getUserID());
			Assert.assertEquals(password, user.getPassword());
			Assert.assertEquals(0, user.getAccountType());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}

	@Test
	public void testFindGalaxy() {
		try {
			String[][] result = cntr.findGalaxy("izw1");
			Assert.assertEquals("izw1", result[0][0]);
		} catch (GalaxyNotExistsException e) {
			System.err.println("Galassia non trovata, assicurarsi di aver caricato il file con le galassie.");
			fail("Galassia 'izw1' non trovata");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Galassie non corrispondento");
		}
	}

	@Test
	public void testInCircle() {
		try {
			String[] inputs = {"240", "1", "0_11_6.5412", "-_12_6_27.66"};
			String[][] result = cntr.galaxyInACircle(inputs);
			for (String s : result[0]) {
				if (s.equals("mrk938")) {
					return;
				}
			}
			fail("Galassia non trovata");
		} catch (PositionTableEmptyException e){
			System.err.println("Non ci sono posizioni memorizzate.");
			fail("Galassia non trovata");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
	
	@Test
	public void testByRed() {
		try {
			String[] inputs = {"0.4", ">", "240"};
			String[][] result = cntr.findRedShift(inputs);
			for (String s : result[0]) {
				if (s.equals("iras09104+4109")) {
					return;
				}
			}
			fail("Risultato errato");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
	
	@Test
	public void testFindRow() {
		try {
			String[] flux = {"siv10"};
			String[][] result = cntr.findFluxes("mrk938", flux);
			for (String s : result[7]) {
				if (s.equals("siv10") && result[4][0].equals("1.46") && result[5][0].equals("0")) {
					return;
				}
			}
			fail("Risultato errato");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
		
	@Test
	public void testRatio() {
		try {
			String[][] result = cntr.fluxRatio("mrk938", "siv10", "nev14", null, null);
			float ratio = Float.parseFloat(result[0][0]);
			if (ratio > 0.66 && ratio < 0.67) {
				return;
			}
			fail("Risultato errato");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
	
	@Test
	public void testFluxStats() {
		try {
			String[][] result = cntr.fluxStats("s1", "oi63", "oi63", null, 5);
			float avg = Float.parseFloat(result[0][0]);
			float med = Float.parseFloat(result[0][1]);
			float dev = Float.parseFloat(result[0][2]);
			float devAbs = Float.parseFloat(result[0][3]);
			if (avg>1.4 && avg<1.5 && med==1 && devAbs==1 && dev>1.3 && dev<1.4) {
				return;
			}
			fail("Risultato errato");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
	
	@Test
	public void testRatioRowCont() {
		try {
			String[][] result = cntr.fluxContRowRatio("izw1", "oi63", "c");
			float ratio = Float.parseFloat(result[0][0]);
			if (ratio > 1.7 && ratio < 1.8) {
				return;
			}
			fail("Risultato errato");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
}
