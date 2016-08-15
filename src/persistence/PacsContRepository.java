package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.PacsContinuousRow;
import exceptions.GalaxyNotExistsException;

public class PacsContRepository {
	private DataSource dataSource;

	public PacsContRepository() {
		dataSource = new DataSource();
	}

	/**
	 * Store a flux_cont into the DB
	 * @param row instance of PacsContinuousRow to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(PacsContinuousRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		final String insert = "insert into flux_cont(ion, flag, val, error, galaxy, aperture) values (?,?,?,?,?,?)";
		
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
			statement.setString(6, row.getAperture());
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
	 * Find a flux_cont by primary key
	 * @param name Primary key value
	 * @param ion Primary Key value
	 * @return returns the found PacsContinuousRow instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public PacsContinuousRow findByPrimaryKey(String galaxy, String ion) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		PacsContinuousRow row = null;
		ResultSet result = null;
		final String query = "select * from flux_cont where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, galaxy);
			statement.setString(2, ion);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (row == null) {
					row = new PacsContinuousRow();
					row.setIon(result.getString("ion"));
					row.setErr(String.valueOf(result.getFloat("error")));
					row.setFlag(result.getBoolean("flag"));
					row.setAperture(result.getString("aperture"));
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
	 * Update an existing flux_cont into the DB
	 * @param row instance of PacsContinuousRow to be updated
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void update(PacsContinuousRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update flux_cont set flag=?, val=?, error=?, aperture=? where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(update);
			statement.setBoolean(1, row.isFlag());
			statement.setFloat(2, Float.parseFloat(row.getVal()));
			if (row.getErr().equals("")) {
				statement.setNull(3, java.sql.Types.FLOAT);
			} else {
				statement.setFloat(3, Float.parseFloat(row.getErr()));
			}
			statement.setString(4, row.getAperture());
			statement.setString(5, row.getGalaxy());
			statement.setString(6, row.getIon());
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
	 * Delete a flux_cont instance from DB
	 * @param name Primary key value
	 * @param ion Primary Key value
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void delete(String galaxy, String ion) throws Exception {
		if (findByPrimaryKey(galaxy, ion) == null) {
			return;
		}
		Connection connection = null;
		PreparedStatement statement = null;

		final String delete = "delete from flux_cont where galaxy=? and ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(delete);
			statement.setString(1, galaxy);
			statement.setString(2, ion);
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
}
