package entity;

public class User {
	private String userID;
	private String password;
	private String mail;
	private String firstName;
	private String lastName;
	private int accountType; //0 if is a normal user; 1 if is an administrator
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getAccountType(){
		return accountType;
	}
	
	public void setAccountType(int accountType){
		this.accountType = accountType;
	}

	public String toString() {
		return "{userID:" + userID + ", password:" + password + ", firstName:" + firstName + ", LastName:"
				+ lastName + ", accountType:" + accountType + "}";
	}
}
