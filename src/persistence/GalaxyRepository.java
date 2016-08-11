package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Galaxy;

public class GalaxyRepository {
	private DataSource dataSource;

	public GalaxyRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a galaxy into the DB
	 * @param galaxy instance of Galaxy to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(Galaxy galaxy) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		//cambiare i valori
		final String insert = "insert into galaxies(distance, metalErr, metalFlag, name, spectralClass, IRSmode) values (?,?,?,?,?,?)";
		//
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(galaxy.getName()) != null) {
				//vanno aggiornati i valori
			}
	
			statement = connection.prepareStatement(insert);
			statement.setFloat(1, galaxy.getDistance());
			statement.setFloat(2, galaxy.getMetalErr());
			statement.setFloat(3, galaxy.getMetalVal());
			statement.setString(4, galaxy.getName());
			statement.setString(5, galaxy.getSpectralClass());
			statement.setString(6, galaxy.getIRSmode());
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
	 * Find a galaxy by primary key
	 * @param name Primary key value
	 * @return returns the found galaxy instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public Galaxy findByPrimaryKey(String name) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		Galaxy galaxy = null;
		ResultSet result = null;
		final String query = "select * from galaxies where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (galaxy == null) {
					galaxy = new Galaxy();
					galaxy.setDistance(result.getFloat("distance"));
					galaxy.setMetalErr(result.getFloat("metalErr"));
					galaxy.setMetalVal(result.getFloat("metalFlag"));
					galaxy.setName(result.getString("name"));
					galaxy.setSpectralClass(result.getString("spectralClass"));
					galaxy.setIRSmode(result.getString("IRSmode"));
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
		return galaxy;
	}
	
	public void adIRS(String name, String mode) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final String query = "update galaxies set IRSmode=? where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, mode);
			statement.setString(2, name);
			result = statement.executeQuery();
			
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
	}
	
	
	//probabilmente inutile perche fai con findByPrimaryKey == null
	/*/**
	 * Find a galaxy by primary key
	 * @param name Primary key value
	 * @return returns the found galaxy instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	/*@SuppressWarnings("unused")
	public boolean exists(String name) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		Galaxy galaxy = null;
		ResultSet result = null;
		final String query = "select * from galaxies where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (galaxy == null) {
					return true;
				}
			} else {
				return false;
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
		return false;
	}*/
}
