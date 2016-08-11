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
	 * Store a galaxy into the DB
	 * @param galaxy instance of Galaxy to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(Brightness brightness) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		//cambiare i valori
		final String insert = "insert into brightness(ion, flag, val, galaxy) values (?,?,?,?)";
		//
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(brightness.getGalaxy(), brightness.getIon()) != null) {
				return;
				//vanno aggiornati i valori
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, brightness.getIon());
			statement.setBoolean(2, brightness.isFlag());
			statement.setFloat(3, brightness.getVal());
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
	
	/**
	 * Find a galaxy by primary key
	 * @param name Primary key value
	 * @return returns the found galaxy instance
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
					brightness.setVal(result.getFloat("val"));
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
