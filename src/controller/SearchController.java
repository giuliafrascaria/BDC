package controller;

/**
* Singleton controller for the search.
*
*/
public class SearchController {

	private static SearchController instance = null;
	
	private SearchController() {
	}
	
    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return SearchController the current controller in the system
     */
	public synchronized static final SearchController getInstance() {
		 if (instance == null)
	            instance = new SearchController();
		 return instance;
	}
	
}
