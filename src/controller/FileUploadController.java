package controller;

public class FileUploadController {
	private static FileUploadController instance = null;

    /**
     * Static method to call to instantiate the controller, it returns
     * the current instance if it is already present in the system.
     *
     * @return FileUploadController the current controller in the system
     */
    public static final synchronized FileUploadController getInstance() {
        if (instance == null)
            instance = new FileUploadController();
        return instance;
    }

    private FileUploadController() {
    }
    
    public void UploadRowSpitzer(String filePath) {
    	
    }
    
    public void UploadContinuos(String filePath) {
    	
    	}

    public void UploadRowPacsBase(String filePath) {
	
    }

    public void UploadRowPacsMultiAper(String filePath) {
	
    }

    public void UploadGalaxies(String filePath) {
	
	}
	
}
