import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
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

	//region Fields
	private String username;
	private String password;
	private String databaseUrl;

	private Dao<Movie, Integer> movieDao;
	private ConnectionSource connectionSource;
	private ArrayList<Movie> movies = new ArrayList<>();

	private final String ID_FIELD_NAME = "id";
	private final String TITLE_FIELD_NAME = "title";
	private final String DIRECTOR_FIELD_NAME = "director";
	private final String YEAR_FIELD_NAME = "year";
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

	public Movie(int id, String name, String director, int year) {
		this.id = id;
		this.name = name;
		this.director = director;
		this.year = year;
	}

	public Movie(String name, String director, int year) {
		this.name = name;
		this.director = director;
		this.year = year;
	}
	//endregion

	//region Methods
	protected void readConfigFile(){
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

	protected void establishConnection(String username, String password){
		try{
			//Creates a connection to movieDB
			connectionSource = new JdbcConnectionSource(databaseUrl, username, password);


			// DAO-init
			movieDao = DaoManager.createDao(connectionSource, Movie.class);

		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	protected void createTable(){
		try{
			if (movieDao.isTableExists()){
				dropTable();
			}

			TableUtils.createTableIfNotExists(connectionSource, Movie.class);
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	protected void dropTable(){
		try{
			TableUtils.dropTable(movieDao,true);
		} catch (SQLException sqle){
			sqle.getMessage();
		}

	}

	protected void fillTableWithInitData(){
		try{
			movies.add(new Movie(1,"Star Wars: The Last Jedi", "Rian Johnson", 2017));
			movies.add(new Movie(2,"Spider-Man: Homecoming", "Jon Watts", 2017));
			movies.add(new Movie(3,"Kingsman: The Golden Circle", "Matthew Vaughn", 2017));
			movies.add(new Movie(4,"Blade Runner 2049", "Denis Villeneuve", 2017));
			movies.add(new Movie(5,"Justice League", "Zack Snyder", 2017));
			movies.add(new Movie(6,"Suicide Squad","David Ayer",2016));
			movies.add(new Movie(7,"Interstellar","Christopher Nolan",2014));
			movies.add(new Movie(8,"Inception","Christopher Nolan",2010));
			movies.add(new Movie(9,"The Dark Knight","Christopher Nolan",2008));
			movies.add(new Movie(10,"Fight Club", "David Fincher",1999));
			movies.add(new Movie(11,"Django Unchained","Quentin Tarantino",2012));
			movies.add(new Movie(12,"The Prestige","Christopher Nolan",2006));
			movies.add(new Movie(13,"The Dark Knight Rises","Christopher Nolan",2012));
			movies.add(new Movie(14,"The Avengers","Joss Whedon",2012));
			movies.add(new Movie(15,"The Lego Movie","Phil Lord, Christopher Miller",2014));
			movies.add(new Movie(16,"Big Hero 6","Don Hall, Chris Williams",2014));
			/*movies.add(new Movie(17,));
			movies.add(new Movie(18));
			movies.add(new Movie(19));
			movies.add(new Movie(20));
			movies.add(new Movie(21));
			movies.add(new Movie(22));
			movies.add(new Movie(23));
			movies.add(new Movie(24));
			movies.add(new Movie(25));*/


			for (Movie i : movies){
				movieDao.createIfNotExists(i);
			}
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	protected void addRow(String title, String director, int year){
		Movie movie = new Movie(title,director,year);
		try {
			movieDao.createIfNotExists(movie);
		} catch (SQLException sqle){
			sqle.getMessage();
		}

	}

	protected void getRowById(int id){

		System.out.println("Getting row where id = " + id);

		try {
			Movie movie = movieDao.queryForId(id);

			System.out.println(movie.toString());

		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	protected void getRowsByYear(int year){

		System.out.println("Getting rows where year = " + year);

		try {
			List<Movie> movies = movieDao.queryBuilder().where().eq(YEAR_FIELD_NAME, year).query();

			for (Movie i : movies){
				System.out.println(i.toString());
			}

			} catch (SQLException sqle){
			sqle.getMessage();
		}
	}

	protected void printAll(){
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

	protected void deleteRow(int id){

		System.out.println("Deleting row:");
		getRowById(id);

		try{
			movieDao.deleteById(id);

			System.out.println("Success!");
		} catch (SQLException sqle){
			sqle.getMessage();
		}
	}
	//endregion

	//region Getters/setters/toString
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public String getDatabaseUrl() {
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
		return String.format("%-10s%-35s%-35s%-30s", id, name, director, year);
	}
	//endregion
}
