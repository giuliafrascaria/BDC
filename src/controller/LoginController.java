package controller;

import entity.User;
import exceptions.UserDoNotExistsException;
import exceptions.WrongPasswordException;
import persistence.UserRepository;

/**
* Singleton controller for the login, called by
* the UserBean.
*
*/
public class LoginController {
	
	private static LoginController instance = null;

    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return LoginController the current controller in the system
     */
    public static final synchronized LoginController getInstance() {
        if (instance == null)
            instance = new LoginController();
        return instance;
    }

    private LoginController() {
    }

    /**
     * Method that performs the login.
     *
     * @param userID identifier of the user.
     * @param password password of the user.
     * @throws UserDoNotExistsException
     */
    public User login(String userID, String password) throws Exception{
    	UserRepository sp = new UserRepository();
    	User user = sp.findByPrimaryKey(userID);
    	if (user == null) {
    		throw new UserDoNotExistsException();
    	}
    	if (password.equals(user.getPassword())){
    		return user;
    	} else {
    		throw new WrongPasswordException();
    	}
    }
}
