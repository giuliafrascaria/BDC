package persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Position;
import exceptions.PositionTableEmptyException;

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
			statement.setFloat(1, Float.parseFloat(position.getRaH()));
			statement.setFloat(2, Float.parseFloat(position.getRaM()));
			statement.setFloat(3, Float.parseFloat(position.getRaS()));
			statement.setBoolean(4, position.getDeSgn());
			statement.setFloat(5, Float.parseFloat(position.getDeD()));
			statement.setFloat(6, Float.parseFloat(position.getDeM()));
			statement.setFloat(7, Float.parseFloat(position.getDeS()));
			statement.setFloat(8, Float.parseFloat(position.getRedShift()));
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
			statement.setFloat(1, Float.parseFloat(pos.getRaH()));
			statement.setFloat(2, Float.parseFloat(pos.getRaM()));
			statement.setFloat(3, Float.parseFloat(pos.getRaS()));
			statement.setBoolean(4, pos.getDeSgn());
			statement.setFloat(5, Float.parseFloat(pos.getDeD()));
			statement.setFloat(6, Float.parseFloat(pos.getDeM()));
			statement.setFloat(7, Float.parseFloat(pos.getDeS()));
			statement.setFloat(8, Float.parseFloat(pos.getRedShift()));
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
					position.setRaH(String.valueOf(result.getFloat("raH")));
					position.setRaM(String.valueOf(result.getFloat("raM")));
					position.setRaS(String.valueOf(result.getFloat("raS")));
					position.setDeSgn(result.getBoolean("deSgn"));
					position.setDeD(String.valueOf(result.getFloat("deD")));
					position.setDeM(String.valueOf(result.getFloat("deM")));
					position.setDeS(String.valueOf(result.getFloat("deS")));
					position.setRedShift(String.valueOf(result.getFloat("redShift")));
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



	/**
	 * Find a position by redshift value
	 * @param redshift value, result range, number of results
	 * @return returns the found position instances
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Position> findByRedShift(String[] inputs) throws Exception {
 		Connection connection = null;
 		PreparedStatement statement = null;
 		List<Position> positions = new ArrayList<Position>();
 		ResultSet result = null;
 		final String query;
 		
 		if(inputs[1].equals("<")){
 			query = "select galaxy, redshift from position where redShift < ?";
 		}else if(inputs[1].equals(">")){
 			query = "select galaxy, redshift from position where redShift > ?";
 		}else{
 			query = "select galaxy, redshift from position where redShift = ?";
 		}
 		try{		
 			connection = this.dataSource.getConnection();
 			statement = connection.prepareStatement(query);
 			statement.setFloat(1, Float.parseFloat(inputs[0]));
 			result = statement.executeQuery();
 			
 			boolean isEmpty = true;

 			while (result.next()) {
 				isEmpty = false;
 				
 				Position position = new Position();
				position = new Position();
				/*if I simple do: position.setRedShift(String.valueOf(result.getFloat("redShift")));
				 * the negative red shift are in exponential form (example: -63E-4) */
				BigDecimal b = new BigDecimal(String.valueOf(result.getFloat("redShift"))); 
				position.setRedShift(String.valueOf(b));
				//
				position.setGalaxy(result.getString("galaxy"));
				positions.add(position);				
 			}
 			if (isEmpty) {
				throw new PositionTableEmptyException();
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
 		return positions;
 	}

	
	public List<Position> findAll () throws Exception {
		Connection connection = null;
		PreparedStatement statement = null;
		List<Position> positions = new ArrayList<Position>();
		ResultSet result = null;
		final String query = "select * from position";
			
		try{		
			connection = this.dataSource.getConnection();	
			statement = connection.prepareStatement(query);
			result = statement.executeQuery();
				
			boolean isEmpty = true;
			
			while (result.next()) {
				isEmpty = false;
				
				Position position = new Position();
				position = new Position();
				position.setRaH(String.valueOf(result.getFloat("raH")));
				position.setRaM(String.valueOf(result.getFloat("raM")));
				position.setRaS(String.valueOf(result.getFloat("raS")));
				position.setDeSgn(result.getBoolean("deSgn"));
				position.setDeD(String.valueOf(result.getFloat("deD")));
				position.setDeM(String.valueOf(result.getFloat("deM")));
				position.setDeS(String.valueOf(result.getFloat("deS")));
				position.setRedShift(String.valueOf(result.getFloat("redShift")));
				position.setGalaxy(result.getString("galaxy"));
				positions.add(position);
			}
			if (isEmpty) {
				throw new PositionTableEmptyException();
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
		return positions;
	}
}



