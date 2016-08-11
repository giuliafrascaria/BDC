package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.SpitzerRow;


public class SpitzerRowRepository {
	private DataSource dataSource;

	public SpitzerRowRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a galaxy into the DB
	 * @param galaxy instance of Galaxy to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(SpitzerRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		//cambiare i valori
		final String insert = "insert into galaxies(ion, flag, val, err, galaxy) values (?,?,?,?,?)";
		//
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(row.getGalaxy(), row.getIon()) != null) {
				//vanno aggiornati i valori
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, row.getIon());
			statement.setBoolean(2, row.isFlag());
			statement.setFloat(3, row.getVal());
			statement.setFloat(4, row.getErr());
			statement.setString(5, row.getGalaxy());
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
	 * Find a galaxy position by primary key
	 * @param galaxyName and ion Primary key value
	 * @return returns the found SpitzerRow instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public SpitzerRow findByPrimaryKey(String galaxy, String ion) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		SpitzerRow row = null;
		ResultSet result = null;
		final String query = "select * from spitzerRow where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, galaxy);
			statement.setString(2, ion);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (row == null) {
					row = new SpitzerRow();
					row.setIon(result.getString("ion"));
					row.setErr(result.getFloat("err"));
					row.setFlag(result.getBoolean("flag"));
					row.setVal(result.getFloat("val"));
					row.setGalaxy(result.getString("galaxy"));
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
		return row;
	}
}
