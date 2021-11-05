package src.main.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.main.model.Film;

// Database Access Object Class {FilmDAO}
public class FilmDAO {
	
//	::::::::::::::::::::::::::::::::DB CONNECTION PARAMS:::::::::::::::::::::::::::::::: 
	private static final String url = "jdbc:mysql://localhost:3306/sakila?useSSl=false";
	private static final String username = "root";
	private static final String password = "root";
//  ::::::::::::::::::::::::::::::::DB CONNECTION PARAMS::::::::::::::::::::::::::::::::	

//	::::::::::::::::::::::::::::::::::::::::::::SQL QUERIES::::::::::::::::::::::::::::::::::::::::::::
	private static final String INSERT_FILM = "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating,director,special_features) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SELECT_FILM_BY_TITLE = "SELECT * FROM film JOIN language ON language.language_id = film.language_id WHERE title LIKE ? ";// OR description LIKE ?";
	private static final String SELECT_FILM_BY_ID = "SELECT * FROM film JOIN language ON language.language_id = film.language_id WHERE film.film_id = ?;";
	private static final String SELECT_FILM_BY_PARAMS = "SELECT * FROM film LIMIT ?, ?;";
	private static final String SELECT_ALL_FILM_BY_LIMIT = "SELECT * FROM film JOIN language ON language.language_id = film.language_id LIMIT ?";
//	private static final String SELECT_ALL_FILM_BY_LIMIT_FROM_LAST = "SELECT * FROM film JOIN language ON language.language_id = film.language_id ORDER BY film_id DESC LIMIT ?";
//	XXXXXXXXXXXXXXXXX:: AVOID USAGE IF <<LARGE DATASET>> ::XXXXXXXXXXXXXXXXX
	private static final String SELECT_ALL_FILM = "SELECT * FROM film JOIN language ON language.language_id = film.language_id";
//	XXXXXXXXXXXXXXXXX:: AVOID USAGE IF <<LARGE DATASET>> ::XXXXXXXXXXXXXXXXX
	
	private static final String UPDATE_FILM = "UPDATE film SET title = ?, description = ?, release_year = ?, language_id = ?, rental_duration = ?, rental_rate = ?, length = ?, replacement_cost = ?, director = ?, special_features = ? WHERE film_id = ?";
	
	private static final String DELETE_FILM = "DELETE FROM film WHERE film_id=?;";
//	X::::::::::::::::::::::::::::::::::::::::::::SQL QUERIES::::::::::::::::::::::::::::::::::::::::::::X
	
//	::::::::::::::::::::::::::::::::::::::::::::AUXILIARY FUNCTIONS::::::::::::::::::::::::::::::::::::::::::::
	
//	::::::::::::::::::::::CONNECTION ESTABLISHMENT HELPER FUNCTION::::::::::::::::::::::
	protected static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url,username,password);
		
		return conn;
	}
//	X::::::::::::::::::::::CONNECTION ESTABLISHMENT HELPER FUNCTION::::::::::::::::::::::X
	
//	::::::::::::::::::::::SELECT BY ID::::::::::::::::::::::
	public Film getFilmById(int film_id) throws SQLException, ClassNotFoundException {
		Film existingFilm;
		Connection connection = getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_BY_ID);
		preparedStatement.setInt(1, film_id);
		ResultSet rs = preparedStatement.executeQuery();
		rs.first();
		
//		Short parameter instance :-->
		/* 
		existingFilm = new Film(rs.getInt("film_id"),
					rs.getString("title"),
					rs.getString("description"),
					rs.getString("director"),
					rs.getString("rating"),
					rs.getString("name")); // Language SQL: (name char(20)) <String> TABLE: language 
		*/
		
//		Actual Parameter instance :-->
		existingFilm = new Film(rs.getInt("film_id"), 
				rs.getString("title"), 
				rs.getString("description"), 
				rs.getInt("release_year"), 
				rs.getInt("language_id"),
				rs.getInt("rental_duration"), 
				rs.getDouble("rental_rate"), 
				rs.getInt("length"), 
				rs.getDouble("replacement_cost"), 
				rs.getString("rating"),
				rs.getString("special_features"), 
				rs.getString("director")); 
		
		return existingFilm;
	}
//	X::::::::::::::::::::::SELECT BY ID::::::::::::::::::::::X
	
//	::::::::::::::::::::::SELECT ALL::::::::::::::::::::::
	public List<Film> getAllFilms() throws ClassNotFoundException, SQLException{
		
		 List<Film> films = new ArrayList<>();
		 Connection connection = getConnection();
		 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILM); 
		 ResultSet rs = preparedStatement.executeQuery();
		 
		
		while(rs.next()) {
			films.add(new Film(rs.getInt("film_id"), 
					rs.getString("title"), 
					rs.getString("description"), 
					rs.getInt("release_year"), 
					rs.getInt("language_id"),
					rs.getInt("rental_duration"), 
					rs.getDouble("rental_rate"), 
					rs.getInt("length"), 
					rs.getDouble("replacement_cost"), 
					rs.getString("rating"),
					rs.getString("special_features"), 
					rs.getString("director")));
		}
		
		return films;
	}
//	X::::::::::::::::::::::SELECT ALL::::::::::::::::::::::X
	
//	::::::::::::::::::::::SELECT BY KEYWORDs::::::::::::::::::::::
	public List<Film> getFilmsByKeyWords(String title,String description) throws SQLException, ClassNotFoundException {
		List<Film> existingFilms = new ArrayList<>();
		Connection connection = getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_BY_TITLE);
		
//		padding with '%' symbol to get relevant results without restriction 
		preparedStatement.setString(1,"%"+title+"%");
//		preparedStatement.setString(2, "%"+description+"%");
		
		ResultSet rs = preparedStatement.executeQuery();
		
		while(rs.next()) {
			existingFilms.add(new Film(rs.getInt("film_id"), 
					rs.getString("title"), 
					rs.getString("description"), 
					rs.getInt("release_year"), 
					rs.getInt("language_id"),
					rs.getInt("rental_duration"), 
					rs.getDouble("rental_rate"), 
					rs.getInt("length"), 
					rs.getDouble("replacement_cost"), 
					rs.getString("rating"),
					rs.getString("special_features"), 
					rs.getString("director")));
		}
		return existingFilms;
	}
//	X::::::::::::::::::::::SELECT BY KEYWORDs::::::::::::::::::::::X
	
//	::::::::::::::::::::::SELECT BY LIMIT::::::::::::::::::::::
	public List<Film> getFilmsByLimit(int limit) throws ClassNotFoundException, SQLException{
		
		 List<Film> films = new ArrayList<>();
		 Connection connection = getConnection();
		 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILM_BY_LIMIT); 
		 preparedStatement.setInt(1, limit);
		 
		 ResultSet rs = preparedStatement.executeQuery();
		 
		
		while(rs.next()) {
			films.add(new Film(rs.getInt("film_id"), 
					rs.getString("title"), 
					rs.getString("description"), 
					rs.getInt("release_year"), 
					rs.getInt("language_id"),
					rs.getInt("rental_duration"), 
					rs.getDouble("rental_rate"), 
					rs.getInt("length"), 
					rs.getDouble("replacement_cost"), 
					rs.getString("rating"),
					rs.getString("special_features"), 
					rs.getString("director")));
		}
		
		return films;
	}
//	X::::::::::::::::::::::SELECT BY LIMIT::::::::::::::::::::::X
	
//	::::::::::::::::::::::SELECT BY PARAMS::::::::::::::::::::::
	public List<Film> getFilmsByLimitParams(int start,int limit) throws ClassNotFoundException, SQLException{
		
		 List<Film> films = new ArrayList<>();
		 Connection connection = getConnection();
		 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_BY_PARAMS); 
		 preparedStatement.setInt(1, start);
		 preparedStatement.setInt(2, limit);
		 		 
		 ResultSet rs = preparedStatement.executeQuery();
		 
		 while(rs.next()) {
			films.add(new Film(rs.getInt("film_id"), 
					rs.getString("title"), 
					rs.getString("description"), 
					rs.getInt("release_year"), 
					rs.getInt("language_id"),
					rs.getInt("rental_duration"), 
					rs.getDouble("rental_rate"), 
					rs.getInt("length"), 
					rs.getDouble("replacement_cost"), 
					rs.getString("rating"),
					rs.getString("special_features"), 
					rs.getString("director")));
		}
		
		return films;
	}	
//	X::::::::::::::::::::::SELECT BY PARAMS::::::::::::::::::::::X
	
//	::::::::::::::::::::::INSERT | ADD [Film]::::::::::::::::::::::
	public void insertFilm(Film film) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILM);
		
		preparedStatement.setString(1, film.getTitle());
		preparedStatement.setString(2, film.getDescription());
		preparedStatement.setInt(3, film.getRelease_year());
		preparedStatement.setInt(4, film.getLanguage_id());
		preparedStatement.setInt(5, film.getRental_duration());
		preparedStatement.setDouble(6, film.getRental_rate());
		preparedStatement.setInt(7, film.getLength());
		preparedStatement.setDouble(8, film.getReplacement_cost());
		preparedStatement.setString(9, film.getRating());
		preparedStatement.setString(10,film.getDirector());
		preparedStatement.setInt(11, film.getSpecial_featuresSetIndex());
		
		preparedStatement.executeUpdate();
		
	}
//	X::::::::::::::::::::::INSERT | ADD [Film]::::::::::::::::::::::X
	
//	::::::::::::::::::::::UPDATE | EDIT [Film]::::::::::::::::::::::
	public boolean updateFilm(Film existingFilm) throws ClassNotFoundException, SQLException {
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FILM);
		preparedStatement.setString(1, existingFilm.getTitle());
		preparedStatement.setString(2, existingFilm.getDescription());
		preparedStatement.setInt(3, existingFilm.getRelease_year());
		preparedStatement.setInt(4, existingFilm.getLanguage_id());
		preparedStatement.setInt(5, existingFilm.getRental_duration());
		preparedStatement.setDouble(6, existingFilm.getRental_rate());
		preparedStatement.setInt(7, existingFilm.getLength());
		preparedStatement.setDouble(8, existingFilm.getReplacement_cost());
		preparedStatement.setString(9, existingFilm.getDirector());
		
		preparedStatement.setInt(10, existingFilm.getSpecial_featuresSetIndex());
		preparedStatement.setInt(11, existingFilm.getFilm_id());
		
		boolean rowUpdated = preparedStatement.executeUpdate() > 0;
		
		return rowUpdated;
	}
//	X::::::::::::::::::::::UPDATE | EDIT [Film]::::::::::::::::::::::X
	
//	::::::::::::::::::::::DELETE [Film]::::::::::::::::::::::
	public boolean deleteFilm(int film_id) throws ClassNotFoundException, SQLException {
		Connection connection = getConnection();
		
		PreparedStatement  ps1= connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0;");
		ps1.executeUpdate();
		
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FILM);
		
		preparedStatement.setInt(1,film_id);
		boolean rowDeleted = preparedStatement.executeUpdate() > 0;
		
		return rowDeleted; 
	}
	
//	X::::::::::::::::::::::DELETE [Film]::::::::::::::::::::::X

//	GET TOTAL NUMBER OF ROWS of [Film]
	public int getTotal()throws ClassNotFoundException, SQLException {
		
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS total FROM sakila.film");
		
		ResultSet rs = preparedStatement.executeQuery();
		rs.first();
		
		int total =  rs.getInt("total");
		
		return total;
	}
//	::::::::::::::::::::::::::::::::::::::::::::APPLICATION UNIT TESTING FUNCTION::::::::::::::::::::::::::::::::::::::::::::
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//	System.out.print(new FilmDAO().getTotal());
	
//	Connection connection = getConnection();
//	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILM_BY_LIMIT_FROM_LAST);
//	preparedStatement.setInt(1, 100);
//	
//	ResultSet rs = preparedStatement.executeQuery();
//	
//	while(rs.next()) {
//		System.out.println(rs.getInt("film_id")+" "+
//				rs.getString("title")+" "+
//				rs.getString("description")+" "+
//				rs.getString("rating")+" "+
//				rs.getString("name"));
//	}
//	
//	connection.close();
//	preparedStatement.close();
}
//	X::::::::::::::::::::::::::::::::::::::::::::APPLICATION UNIT TESTING FUNCTION::::::::::::::::::::::::::::::::::::::::::::X
	
//	X::::::::::::::::::::::::::::::::::::::::::::AUXILIARY FUNCTIONS::::::::::::::::::::::::::::::::::::::::::::X
//}





