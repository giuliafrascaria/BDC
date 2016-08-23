package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import controller.LoginController;
import controller.RegistrationController;
import entity.User;

public class Req1_2_3Test {
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
}
