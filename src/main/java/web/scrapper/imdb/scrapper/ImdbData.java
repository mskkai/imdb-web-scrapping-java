package web.scrapper.imdb.scrapper;

public class ImdbData {

	private String imdbUrl;
	private String movieName;
	private String movieRating;
	private Integer currentPosition;
	
	public ImdbData(String imdbUrl, String movieName, String movieRating, Integer currentPosition ){
		this.imdbUrl = imdbUrl;
		this.movieName = movieName;
		this.movieRating = movieRating;
		this.currentPosition = currentPosition;
	}

	public String getImdbUrl() {
		return imdbUrl;
	}

	public void setImdbUrl(String imdbUrl) {
		this.imdbUrl = imdbUrl;
	}

	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}

	public Integer getCurrentPosition() {
		return currentPosition;
	}

	public  void setCurrentPosition(Integer currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	
}
