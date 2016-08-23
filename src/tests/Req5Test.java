package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import controller.QueryController;
import exceptions.GalaxyNotExistsException;

public class Req5Test {

	@Test
	public void testFindGalaxy() {
		QueryController cntr = QueryController.getInstance();
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

}
