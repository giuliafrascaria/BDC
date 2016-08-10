package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.AlternativeName;

public class AltNameRepository {
	private DataSource dataSource;

	public AltNameRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a galaxy into the DB
	 * @param galaxy instance of Galaxy to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(AlternativeName alt) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		//cambiare i valori
		final String insert = "insert into galaxies(name, galaxy,) values (?,?)";
		//
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(alt.getName()) != null) {
				//vanno aggiornati i valori
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, alt.getName());
			statement.setString(2, alt.getGalaxy());
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
	public AlternativeName findByPrimaryKey(String name) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		AlternativeName alt = null;
		ResultSet result = null;
		final String query = "select * from altName where name=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, name);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (alt == null) {
					alt = new AlternativeName();
					alt.setName(result.getString("name"));
					alt.setGalaxy(result.getString("galaxy"));
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
		return alt;
	}
}
