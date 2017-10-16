import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

@DatabaseTable(tableName = "movie")
public class Movie {

	public static void main(String[] args) throws Exception {
		readConfigFile();

		//Creates a connection to movieDB and creating DAO
		establishConnection(username,password);

		//creating table
		createTable();

		//fill table with Init-data
		fillTableWithInitData();

		//adding row
		addRow("Wonder Woman", "Patty Jenkins", 2017);

		//getting movie by ID
		getRowById();

		//getting movies by year (only 2017 movies)
		getRowsByYear();

		//delete methods
		//deleteRow(2);
		//dropTable();

		printAll();
	}

	//region Fields
	private static String username;
	private static String password;
	private static String databaseUrl;

	private static Dao<Movie, Integer> movieDao;
	private static ConnectionSource connectionSource;
	private static ArrayList<Movie> movies = new ArrayList<Movie>();
	private static Scanner scanner = new Scanner(System.in);

	private static final String ID_FIELD_NAME = "id";
	private static final String TITLE_FIELD_NAME = "title";
	private static final String DIRECTOR_FIELD_NAME = "director";
	private static final String YEAR_FIELD_NAME = "year";
	//endregion

	//region Database-fields
	@DatabaseField(generatedId = true, dataType = DataType.INTEGER, columnName = ID_FIELD_NAME)
	private int id;
	@DatabaseField(dataType = DataType.STRING, columnName = TITLE_FIELD_NAME)
	private String name;
	@DatabaseField(dataType = DataType.STRING, columnName = DIRECTOR_FIELD_NAME)
	private String director;
	@DatabaseField(dataType = DataType.INTEGER, columnName = YEAR_FIELD_NAME)
	private int year;
	//endregion

	//region Constructors
	public Movie(){
		//Empty constructor
	}

	private Movie(int id, String name, String director, int year) {
		this.id = id;
		this.name = name;
		this.director = director;
		this.year = year;
	}

	private Movie(String name, String director, int year) {
		this.name = name;
		this.director = director;
		this.year = year;
	}
	//endregion

	//region Methods
	private static void readConfigFile(){
		Properties props = new Properties();
		InputStream input = null;

		try{
			String filePath = "config.properties";
			input = Movie.class.getClassLoader().getResourceAsStream(filePath);

			if (input == null){
				System.out.println("Unable to read file at " + filePath);
				return;
			}

			props.load(input);

			setUsername(props.getProperty("username"));
			setPassword(props.getProperty("password"));
			setDatabaseUrl(props.getProperty("databaseUrl"));

		} catch (IOException ioex){
			ioex.getMessage();
		}
	}

	private static void establishConnection(String username, String password){
		try{
			//Creates a connection to movieDB
			connectionSource = new JdbcConnectionSource(databaseUrl, username, password);

			// DAO-init
			movieDao = DaoManager.createDao(connectionSource, Movie.class);
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	private static void createTable(){
		try{
			TableUtils.createTableIfNotExists(connectionSource,Movie.class);
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	private static void dropTable(){
		try{
			TableUtils.dropTable(movieDao,true);
		} catch (SQLException sqle){
			sqle.getMessage();
		}

	}

	private static void fillTableWithInitData(){
		try{
			movies.add(new Movie(1,"Star Wars: The Last Jedi", "Rian Johnson", 2017));
			movies.add(new Movie(2,"Spider-Man: Homecoming", "Jon Watts", 2017));
			movies.add(new Movie(3,"Kingsman: The Golden Circle", "Matthew Vaughn", 2017));
			movies.add(new Movie(4,"Blade Runner 2049", "Denis Villeneuve", 2017));

			for (Movie i : movies){
				movieDao.createIfNotExists(i);
			}
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	private static void addRow(String title, String director, int year){
		Movie movie = new Movie(title,director,year);
		try {
			movieDao.createIfNotExists(movie);
		} catch (SQLException sqle){
			sqle.getMessage();
		}

	}

	private static void getRowById(){

		System.out.print("Enter movie-id: ");
		int id = Integer.parseInt(scanner.nextLine());
		System.out.println("Getting row where id = " + id);

		try {
			Movie movie = movieDao.queryForId(id);
			System.out.println(movie.toString());

		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	private static void getRowsByYear(){
		System.out.print("Enter year: ");
		int year = Integer.parseInt(scanner.nextLine());
		System.out.println("Getting rows where years = " + year);

		try {
			List<Movie> movies = movieDao.queryBuilder().where().eq(YEAR_FIELD_NAME, year).query();

			for (Movie i : movies){
				System.out.println(i.toString());
			}

			} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	private static void printAll(){
		try{
			List<Movie> movies = movieDao.queryForAll();

			System.out.println("Printing all rows in table: ");

			for (Movie i : movies){
				System.out.println(i.toString());
			}

		} catch (SQLException sqle){
			sqle.getMessage();
		}

	}

	private static void deleteRow(int id){
		try{
			movieDao.deleteById(id);

		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}
	//endregion

	//region Getters/setters/toString
	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Movie.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Movie.password = password;
	}

	public static void setDatabaseUrl(String databaseUrl) {
		Movie.databaseUrl = databaseUrl;
	}

	public static String getDatabaseUrl() {
		return databaseUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "id: " + id + ", name: " + name + ", director: " + director + ", year: " + year;
	}
	//endregion
}
