package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Brightness;

public class BrightnessRepository {
	private DataSource dataSource;

	public BrightnessRepository() {
		dataSource = new DataSource();
	}
	
	/**
	 * Store a brightness into the DB
	 * @param brightness instance of Brightness to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(Brightness brightness) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		final String insert = "insert into brightness(ion, flag, val, galaxy) values (?,?,?,?)";
		/*nel database ion è varchar
		 * flag è boolean
		 * val è float
		 * galaxy è varchar
		 */
		
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(brightness.getGalaxy(), brightness.getIon()) != null) {
				update(brightness);
				return;
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, brightness.getIon());
			statement.setBoolean(2, brightness.isFlag());
			//se qui stampo Float.parseFloat(brightness.getVal()) il valore è esatto (ad esempio 42.3)
			//ma poi nel database salva sbagliato (ad esempio 42.2999984741211)
			statement.setFloat(3, Float.parseFloat(brightness.getVal()));
			statement.setString(4, brightness.getGalaxy());
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
	
	public void update (Brightness br) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update brightness set flag=?, val=? where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(update);
			statement.setBoolean(1, br.isFlag());
			statement.setFloat(2, Float.parseFloat(br.getVal()));
			statement.setString(3, br.getGalaxy());
			statement.setString(4, br.getIon());
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
	 * Find a brightness by primary key
	 * @param galaxyName Primary key value
	 * @param ion Primary key value
	 * @return returns the found brightness instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public Brightness findByPrimaryKey(String galaxyName, String ion) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		Brightness brightness = null;
		ResultSet result = null;
		final String query = "select * from brightness where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, galaxyName);
			statement.setString(2, ion);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (brightness == null) {
					brightness = new Brightness();
					brightness.setIon(result.getString("ion"));
					brightness.setFlag(result.getBoolean("flag"));
					brightness.setVal(String.valueOf(result.getFloat("val")));
					brightness.setGalaxy(result.getString("galaxy"));
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
		return brightness;
	}
}
