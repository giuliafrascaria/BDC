package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.PacsRow;
import exceptions.GalaxyNotExistsException;

public class PacsRowRepository {
	private DataSource dataSource;

	public PacsRowRepository() {
		dataSource = new DataSource();
	}

	/**
	 * Store a flux_pacs into the DB
	 * @param row instance of PacsRow to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(PacsRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		final String insert = "insert into flux_pacs(ion, flag, val, error, galaxy, aperture) values (?,?,?,?,?,?)";
		
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(row.getGalaxy(), row.getIon(), row.getAperture()) != null) {
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
	 * Find a flux_pacs by primary key
	 * @param name Primary key value
	 * @param ion Primary Key value
	 * @param aperture Primary Key value
	 * @return returns the found PacsRow instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public PacsRow findByPrimaryKey(String galaxy, String ion, String aperture) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		PacsRow row = null;
		ResultSet result = null;
		final String query = "select * from flux_pacs where galaxy=? and ion=? and aperture=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, galaxy);
			statement.setString(2, ion);
			statement.setString(3, aperture);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (row == null) {
					row = new PacsRow();
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
	 * Update an existing flux_pacs into the DB
	 * @param row instance of PacsRow to be updated
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void update(PacsRow row) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update flux_pacs set flag=?, val=?, error=? where aperture=? and galaxy=? and ion=?";

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
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
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
	 * Delete a flux_pacs instance from DB
	 * @param name Primary key value
	 * @param ion Primary Key value
	 * @param aperture Primary Key value
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void delete(String galaxy, String ion, String aperture) throws Exception {
		if (findByPrimaryKey(galaxy, ion, aperture) == null) {
			return;
		}
		Connection connection = null;
		PreparedStatement statement = null;

		final String delete = "delete from flux_pacs where galaxy=? and ion=? and aperture=?";
		
		try{		
			connection = this.dataSource.getConnection();
			statement = connection.prepareStatement(delete);
			statement.setString(1, galaxy);
			statement.setString(2, ion);
			statement.setString(3, aperture);
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
	 * say if a ion is a Pacs ion
	 * @param ion to find
	 * @return returns a boolean
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public boolean findIon(String ion) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		final String query = "select * from flux_pacs where ion=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, ion);
			result = statement.executeQuery();
			
			if (result.next()) {
				return true;
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
	}
	
	public List<Float> findAllRowOfClass(String ion, String spClass, String aper) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Float> values = new ArrayList<Float>();
		ResultSet result = null;
		final String query;
		if (aper == null) {
			query = "select val from flux_pacs join galaxy on galaxy=name  where spectralClass=? and ion=?";
		} else {
			query = "select val from flux_pacs join galaxy on galaxy=name  where spectralClass=? and ion=? and aperture=?";	
		}
			
		try{		
			connection = this.dataSource.getConnection();	
			statement = connection.prepareStatement(query);
			statement.setString(1, spClass);
			statement.setString(2, ion);
			if (aper != null) {
				statement.setString(3, aper);
			}
			result = statement.executeQuery();

			while (result.next()) {
				values.add(result.getFloat("val"));
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
		return values;
	}
}
