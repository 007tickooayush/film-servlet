package src.main.model;

import java.util.HashMap;
import java.util.Map;

//MODEL CLASS [Film] 
public class Film {

	private Integer film_id;
	private String title;
	private String description;
	private Integer release_year;
	private int language_id;
	private int rental_duration;
	private double rental_rate;
	private int length;
	private double replacement_cost;
	private String rating;
	private String special_features;
	private String director;
	private String language;
//	using static keyword to customize/limit server response 
	private static Map<Integer,String> sf_map;
	private static int special_features_index;
	
//	"" -> 0
//	Trailers -> 1
//	Commentaries -> 2
//	Trailers,Commentaries -> 3
//	Deleted Scenes -> 4
//	Trailers,Deleted Scenes -> 5
//	Commentaries,Deleted Scenes -> 6
//	Trailers,Commentaries,Deleted Scenes -> 7
//	Behind the Scenes -> 8
//	Trailers,Behind the Scenes -> 9
//	Commentaries,Behind the Scenes -> 10
//	Trailers,Commentaries,Behind the Scenes -> 11
//	Deleted Scenes,Behind the Scenes -> 12
	
//	TODO: create HashMap to get special_features set's MYSQL set index
	private Film(){
		sf_map = new HashMap<>();
		sf_map.put(1, "Trailers");
		sf_map.put(2, "Commentaries");
		sf_map.put(3, "Trailers,Commentaries");
		sf_map.put(4, "Deleted Scenes");
		sf_map.put(5, "Trailers,Deleted Scenes");
		sf_map.put(6, "Commentaries,Deleted Scenes");
		sf_map.put(7, "Trailers,Commentaries,Deleted Scenes");
		sf_map.put(8, "Behind the Scenes");
		sf_map.put(9, "Trailers,Behind the Scenes");
		sf_map.put(10, "Commentaries,Behind the Scenes");
		sf_map.put(11, "Trailers,Commentaries,Behind the Scenes");
		sf_map.put(12, "Deleted Scenes,Behind the Scenes");
		
	}
	
	public Film(Integer film_id,String title,String description,String director,String rating,String language) {
		this();
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.director = director;
		this.rating = rating;
		this.language = language;
	}
	
//	!!x String director, special_features x
	public Film(String title, String description, Integer release_year, int language_id,
			int rental_duration, double rental_rate, int length, double replacement_cost, String rating){
		this();
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
	}
	
//	^ + Integer film_id , String director
	public Film(Integer film_id,String title, String description, Integer release_year, 
			int language_id,int rental_duration,double rental_rate,
			int length,double replacement_cost,String rating,String director) {
		this();
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.director = director;
	}
	
//	^ !!x Integer film_id ,String special_features x!! , ^ + String director
	public Film(String title, String description, Integer release_year, 
			int language_id,int rental_duration,double rental_rate,
			int length,double replacement_cost,String rating,String director) {
		this();
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.director = director;
	}
	
	public Film(String title, String description, Integer release_year, int language_id,
			int rental_duration, double rental_rate, int length, double replacement_cost, String rating,
			String special_features, String director) {
		this();
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
		this.director = director;
	}
	
	public Film(Integer film_id, String title, String description, Integer release_year, int language_id,
			int rental_duration, double rental_rate, int length, double replacement_cost, String rating,
			String special_features, String director) {
		this();
		this.film_id = film_id;
		this.title = title;
		this.description = description;
		this.release_year = release_year;
		this.language_id = language_id;
		this.rental_duration = rental_duration;
		this.rental_rate = rental_rate;
		this.length = length;
		this.replacement_cost = replacement_cost;
		this.rating = rating;
		this.special_features = special_features;
		this.director = director;
	}

	public Integer getFilm_id() {
		return film_id;
	}
	public void setFilm_id(Integer film_id) {
		this.film_id = film_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getRelease_year() {
		return release_year;
	}
	public void setRelease_year(Integer release_year) {
		this.release_year = release_year;
	}
	public int getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getRental_duration() {
		return rental_duration;
	}
	public void setRental_duration(int rental_duration) {
		this.rental_duration = rental_duration;
	}
	public double getRental_rate() {
		return rental_rate;
	}
	public void setRental_rate(double rental_rate) {
		this.rental_rate = rental_rate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getReplacement_cost() {
		return replacement_cost;
	}
	public void setReplacement_cost(double replacement_cost) {
		this.replacement_cost = replacement_cost;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getSpecial_featuresValue() {
		return special_features;
	}
	
	public int getSpecial_featuresSetIndex() {
		return special_features_index;
	}
	
	private void setSpecial_featuresValue(String special_features) {
		this.special_features = special_features;
	}

	public void setSpecial_featuresIndex(int special_features_index) {
		this.special_features_index = special_features_index;
		this.setSpecial_featuresValue(sf_map.get(special_features_index));
	}
	
	
}


