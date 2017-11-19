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

	private Movie(int id, String name, String director, int year) {
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
			movies.add(new Movie(17,"Blade Runner","Ridley Scott",1982));
			movies.add(new Movie(18,"The Wolf of Wall Street","Martin Scorsese",2013));
			movies.add(new Movie(19,"Fargo","Joel Coen, Ethan Coen",1996));
			movies.add(new Movie(20,"Forrest Gump", "Robert Zemeckis", 1994));
			movies.add(new Movie(21,"Pulp Fiction", "Quentin Tarantino", 1994));
			movies.add(new Movie(22,"Se7en", "David Fincher", 1995));
			movies.add(new Movie(23,"The Silence of the Lambs", "Jonathan Demme", 1991));
			movies.add(new Movie(24,"The Shawshank Redemption", "Frank Darabont", 1994));
			movies.add(new Movie(25,"The Godfather", "Francis Ford Coppola", 1972));
			movies.add(new Movie(26,"Schindler's List","Steven Spielberg",1993));
			movies.add(new Movie(27,"Goodfellas","Martin Scorsese",1990));
			movies.add(new Movie(28,"Spirited Away","Hayao Miyazaki, Kirk Wise",2001));
			movies.add(new Movie(29,"American History X","Tony Kaye",1998));
			movies.add(new Movie(30,"The Lion King","Roger Allers, Rob Minkoff",1994));
			movies.add(new Movie(31,"Memento","Christopher Nolan",2000));
			movies.add(new Movie(32,"Alien","Ridley Scott",1979));
			movies.add(new Movie(33,"Braveheart","Mel Gibson",1995));
			movies.add(new Movie(34,"Reservoir Dogs","Quentin Tarantino",1992));
			movies.add(new Movie(35,"A Clockwork Orange","Stanley Kubrick",1971));
			movies.add(new Movie(36,"Inglourious Basterds","Quentin Tarantino, Eli Roth",2009));
			movies.add(new Movie(37,"Dunkirk","Christopher Nolan",2017));
			movies.add(new Movie(38,"Monty Python and the Holy Grail","Terry Gilliam, Terry Jones",1975));
			movies.add(new Movie(39,"Good Will Hunting","Gus Van Sant",1997));
			movies.add(new Movie(40,"The Hunt","Thomas Vinterberg",2012));
			movies.add(new Movie(41,"Scarface","Brian De Palma",1983));
			movies.add(new Movie(42,"Indiana Jones and the Last Crusade","Steven Spielberg",1989));
			movies.add(new Movie(43,"Die Hard","John McTiernan",1988));
			movies.add(new Movie(44,"My Neighbor Totoro","Hayao Miyazaki",1988));
			movies.add(new Movie(45,"Howl's Moving Castle","Hayao Miyazaki",2004));

			for (Movie i : movies){
				movieDao.createIfNotExists(i);
			}
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

	protected void addRow(String title, String director, int year){
		Movie movie = new Movie(title,director,year);
		try {
			movieDao.createIfNotExists(movie);
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

	@Override
	public String toString() {
		return String.format("%-10s%-35s%-35s%-30s", id, name, director, year);
	}
	//endregion
}
