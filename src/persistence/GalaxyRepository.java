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
		
		final String insert = "insert into galaxy(distance, metalErr, metalVal, name, spectralClass, IRSmode) values (?,?,?,?,?,?)";

		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(galaxy.getName()) != null) {
				update(galaxy);
				return;
			}
	
			statement = connection.prepareStatement(insert);
			if (!galaxy.getDistance().equals("")) {
				statement.setFloat(1, Float.parseFloat(galaxy.getDistance()));
			} else {
				statement.setNull(1, java.sql.Types.FLOAT);
			}
			if (!galaxy.getMetalErr().equals("")) {
				statement.setFloat(2, Float.parseFloat(galaxy.getMetalErr()));
			} else {
				statement.setNull(2, java.sql.Types.FLOAT);
			}
			if (!galaxy.getMetalVal().equals("")) {
				statement.setFloat(3, Float.parseFloat(galaxy.getMetalVal()));
			} else {
				statement.setNull(3, java.sql.Types.FLOAT);
			}
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
	 * Update an existing galaxy into the DB
	 * @param galaxy instance of Galaxy to be updated
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void update(Galaxy galaxy) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update galaxy set distance=?, metalErr=?, metalVal=?, name=?, spectralClass=?, IRSmode=? where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(update);
			if (!galaxy.getDistance().equals("")) {
				statement.setFloat(1, Float.parseFloat(galaxy.getDistance()));
			} else {
				statement.setNull(1, java.sql.Types.FLOAT);
			}
			if (!galaxy.getMetalErr().equals("")) {
				statement.setFloat(2, Float.parseFloat(galaxy.getMetalErr()));
			} else {
				statement.setNull(2, java.sql.Types.FLOAT);
			}
			if (!galaxy.getMetalVal().equals("")) {
				statement.setFloat(3, Float.parseFloat(galaxy.getMetalVal()));
			} else {
				statement.setNull(3, java.sql.Types.FLOAT);
			}
			statement.setString(4, galaxy.getName());
			statement.setString(5, galaxy.getSpectralClass());
			statement.setString(6, galaxy.getIRSmode());
			statement.setString(7, galaxy.getName());
			statement.executeUpdate();
			
		}finally{
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
		final String query = "select * from galaxy where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (galaxy == null) {
					galaxy = new Galaxy();
					galaxy.setDistance(String.valueOf(result.getFloat("distance")));
					galaxy.setMetalErr(String.valueOf(result.getFloat("metalErr")));
					galaxy.setMetalVal(String.valueOf(result.getFloat("metalVal")));
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
	
	/**
	 * Update a galaxy into the DB
	 * @param name Primary Key of galaxy table
	 * @param mode IRSmode of the galaxy to add in the DB
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void adIRS(String name, String mode) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String query = "update galaxy set IRSmode=? where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, mode);
			statement.setString(2, name);
			statement.executeQuery();
			
		}finally{
			// release resources
			if(statement != null){
				statement.close();
			}
			if(connection  != null){
				connection.close();
			}
		}
	}
}
