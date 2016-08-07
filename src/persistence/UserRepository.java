package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.User;
import exceptions.UserAlreadyRegistredException;


public class UserRepository {
	private DataSource dataSource;

	public UserRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a user into the DB
	 * @param user instance of User to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(User user) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String insert = "insert into users(userID, password, firstName, lastName, accountType, mail) values (?,?,?,?,?,?)";
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(user.getUserID()) != null) {
				throw new UserAlreadyRegistredException();
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, user.getUserID());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setInt(5, user.getAccountType());
			statement.setString(6, user.getMail());
			statement.executeUpdate();

		}
		finally{
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}
	}
	
	/**
	 * Delete a User instance from DB
	 * @param user instance of User to be deleted
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void delete(User user) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		final String insert = "delete from users where userID = ?";
		
		try{		
			connection = this.dataSource.getConnection();

			statement = connection.prepareStatement(insert);
			statement.setString(1, user.getUserID());
			statement.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}
	}

	/**
	 * Find a user by primary key
	 * @param userID Primary key value
	 * @return returns the found user instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public User findByPrimaryKey(String userID) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		User user = null;
		ResultSet result = null;
		final String query = "select * from users where userID=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, userID);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (user == null) {
					user = new User();
					user.setUserID(result.getString("userID"));
					user.setFirstName(result.getString("firstname"));
					user.setLastName(result.getString("lastname"));
					user.setPassword(result.getString("password"));
					user.setAccountType(result.getInt("accountType"));
					user.setMail(result.getString("mail"));
				}
			} else {
				return null;
			}
		}finally{
			// release resources
			if(result != null){
				result.close();
			}
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}
		return user;
	}

	/**
	 * Returns all users stored into the DB
	 * @return User list (ordered by code) containing all instances stored into the DB
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public List<User> findAll() throws SQLException {
		List<User> users = null;
		Connection connection = null;
		final String query = "select * from users order by userID";
		PreparedStatement statement = null;
		ResultSet result = null;
		User prevUser = null;
		try{
			users = new LinkedList<User>();
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
			while (result.next()) {
				if (prevUser == null || result.getString("userID") != prevUser.getUserID()) {
					User user = new User();
					user.setUserID(result.getString("userID"));
					user.setFirstName(result.getString("firstname"));
					user.setLastName(result.getString("lastname"));
					user.setPassword(result.getString("password"));
					user.setAccountType(result.getInt("accountType"));
					user.setMail(result.getString("mail"));
					prevUser = user;
	
					users.add(user);
				}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// release resources
			if(result != null){
				result.close();
			}
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}

		return users;
	}
}
