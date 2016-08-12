package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Position;

public class PositionRepository {	
	private DataSource dataSource;

	public PositionRepository() {
		dataSource = new DataSource();
	}
	
	/**
	 * Store a position into the DB
	 * @param position instance of Position to be stored
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void persist(Position position) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		
		final String insert = "insert into position(raH, raM, raS, deSgn, deD, deM, deS, redShift, galaxy) values (?,?,?,?,?,?,?,?,?)";
		
		try{		
			connection = this.dataSource.getConnection();
	
			if (findByPrimaryKey(position.getGalaxy()) != null) {
				update (position);
				return;
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
	 * Update an existing position of the DB
	 * @param pos Position instance to be updated
	 * @throws SQLException Throws SQLException if closing functions fail
	 */
	public void update (Position pos) throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		final String update = "update position set raH=?, raM=?, raS=?, deSgn=?, deD=?, deM=?, deS=?, redShift=? where galaxy=?";
		
		try{		
			connection = this.dataSource.getConnection();
			
			statement = connection.prepareStatement(update);
			statement.setFloat(1, pos.getRaH());
			statement.setFloat(2, pos.getRaM());
			statement.setFloat(3, pos.getRaS());
			statement.setBoolean(4, pos.getDeSgn());
			statement.setFloat(5, pos.getDeD());
			statement.setFloat(6, pos.getDeM());
			statement.setFloat(7, pos.getDeS());
			statement.setFloat(8, pos.getRedShift());
			statement.setString(9, pos.getGalaxy());
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
		final String query = "select * from position where galaxy=?";
		
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
