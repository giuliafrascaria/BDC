package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.SpitzerRow;
import exceptions.GalaxyNotExistsException;


public class SpitzerRowRepository {
	private DataSource dataSource;

	public SpitzerRowRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a flux_spitzer into the DB
	 * @param row instance of SpitzerRow to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(SpitzerRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		final String insert = "insert into flux_spitzer(ion, flag, val, error, galaxy) values (?,?,?,?,?)";
		
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(row.getGalaxy(), row.getIon()) != null) {
				update(row);
				return;
			}
			GalaxyRepository gr = new GalaxyRepository();
			if(gr.findByPrimaryKey(row.getGalaxy()) == null) {
				AltNameRepository altr = new AltNameRepository();
				if (altr.findByPrimaryKey(row.getGalaxy()) != null) {
					row.setGalaxy(altr.findByPrimaryKey(row.getGalaxy()).getGalaxy());
				}
				throw new GalaxyNotExistsException();
			}
	
			statement = connection.prepareStatement(insert);
			statement.setString(1, row.getIon());
			statement.setBoolean(2, row.isFlag());
			statement.setFloat(3, Float.parseFloat(row.getVal()));
			if (row.getErr().equals("")) {
				statement.setNull(4, java.sql.Types.FLOAT);
			} else {
				statement.setFloat(4, Float.parseFloat(row.getErr()));
			}
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
	 * Find a flux_spitzer by primary key
	 * @param name Primary key value
	 * @param ion Primary Key value
	 * @return returns the found SpitzerRow instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public SpitzerRow findByPrimaryKey(String galaxy, String ion) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		SpitzerRow row = null;
		ResultSet result = null;
		final String query = "select * from flux_spitzer where galaxy=? and ion=?";
		
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
					row.setErr(String.valueOf(result.getFloat("error")));
					row.setFlag(result.getBoolean("flag"));
					row.setVal(String.valueOf(result.getFloat("val")));
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
	
	/**
	 * Update an existing flux_spitzer into the DB
	 * @param row instance of SpitzerRow to be updated
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void update(SpitzerRow row) throws Exception{
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update flux_spitzer set flag=?, val=?, error=? where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(update);
			statement.setBoolean(1, row.isFlag());
			statement.setFloat(2, Float.parseFloat(row.getVal()));
			statement.setFloat(3, Float.parseFloat(row.getErr()));
			statement.setString(4, row.getGalaxy());
			statement.setString(5, row.getIon());
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
}
