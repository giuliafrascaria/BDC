package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Galaxy;
import entity.Position;

public class PosistionRepository {	
	private DataSource dataSource;

	public PosistionRepository() {
		dataSource = new DataSource();
	}
	/**
	 * Store a position into the DB
	 * @param posiztion instance of Position to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(Position position) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		//cambiare i valori
		final String insert = "insert into galaxies(raH, raM, raS, deSgn, deD, deM, deS, redShift, galaxy) values (?,?,?,?,?,?,?,?,?)";
		//
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(position.getGalaxy()) != null) {
				//vanno aggiornati i valori
			}
			
			//se la galassia non è nel DB ne creiamo una con tutti i campi nulli tranne il nome (mi pare galli avesse detto di fare cosi)
			GalaxyRepository rep = new GalaxyRepository();
			if (rep.findByPrimaryKey(position.getGalaxy()) == null) {
				Galaxy galaxy = new Galaxy();
				galaxy.setName(position.getGalaxy());
				rep.persist(galaxy);
			}
	
			statement = connection.prepareStatement(insert);
			statement.setFloat(1, position.getRaH());
			statement.setFloat(2, position.getRaM());
			statement.setFloat(3, position.getRaS());
			statement.setBoolean(4, position.getDeSgn());
			statement.setFloat(5, position.getDeD());
			statement.setFloat(6, position.getDeM());
			statement.setFloat(7, position.getDeS());
			statement.setFloat(8, position.getRedShift());
			statement.setString(9, position.getGalaxy());
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
	 * Find a position by primary key
	 * @param galaxyName Primary key value
	 * @return returns the found position instance
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public Position findByPrimaryKey(String galaxyName) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		Position position = null;
		ResultSet result = null;
		final String query = "select * from positions where galaxy=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(query);
			statement.setString(1, galaxyName);
			result = statement.executeQuery();
			
			if (result.next()) {
				if (position == null) {
					position = new Position();
					position.setRaH(result.getFloat("raH"));
					position.setRaM(result.getFloat("raM"));
					position.setRaS(result.getFloat("raS"));
					position.setDeSgn(result.getBoolean("deSgn"));
					position.setDeD(result.getFloat("deD"));
					position.setDeM(result.getFloat("deM"));
					position.setDeS(result.getFloat("deS"));
					position.setRedShift(result.getFloat("redShift"));
					position.setGalaxy(result.getString("galaxy"));
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
		return position;
	}
}
