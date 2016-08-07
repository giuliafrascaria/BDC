package controller;

import entity.User;
import exceptions.UserAlreadyRegistredException;
import persistence.UserRepository;

/**
* Singleton controller for the registration.
*
*/
public class RegistrationController {
	
	private static RegistrationController instance = null;

    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return RegistrationController the current controller in the system
     */
	public synchronized static final RegistrationController getInstance() {
        if (instance == null)
            instance = new RegistrationController();
        return instance;
    }

    private RegistrationController() {
    }
    
    /**
    * Perform the registration by creating a new user.
    *
    * @param firstName first name of the user.
    * @param lastName last name of the user.
    * @param accountType type of the account for the user.
    * @param password password of the user.
    * @throws UserAlreadyRegistredException
    */
    public void registerUser(String firstName, String lastName, String userID, String mail,
    						String password, int accountType) throws Exception{
    	User user = new User();
    	user.setUserID(userID);
    	user.setFirstName(firstName);
    	user.setLastName(lastName);
    	user.setPassword(password);
    	user.setAccountType(accountType);
    	user.setMail(mail);
		UserRepository us = new UserRepository();
		us.persist(user);
		return;
    }
}