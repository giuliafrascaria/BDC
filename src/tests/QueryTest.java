package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import controller.QueryController;
import exceptions.GalaxyNotExistsException;
import exceptions.PositionTableEmptyException;

public class QueryTest {
	private QueryController cntr = QueryController.getInstance();

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
		} catch (Exception e) {
			e.printStackTrace();
			fail("Errore");
		}
	}
}
