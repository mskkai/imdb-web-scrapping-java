package web.scrapper.imdb.scrapper;

import java.io.IOException;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Application to scrap imdb site to get movie rating
 *
 */
public class IMDBWebScrapper {
	private static final String IMDB_URL = "http://www.imdb.com/list/ls057823854/";

	public static void main(String[] args) {

		//Scanner to get the input movie name
		System.out.println("Enter the Movie Name to get IMDB Rating: ");
		String movieName = new Scanner(System.in).nextLine();
		System.out.println("Searching for the " + movieName + " movie's IMDB Rating..........");

		//Initializing the IMDB object with default URL, and entered movie name
		ImdbData imdbData = new ImdbData(IMDB_URL, movieName, "", 1);
		imdbData = getRating(imdbData);
		if (imdbData.getMovieRating() == "") {
			System.out.println("Movie not found in IMDB database");
		} else {
			System.out.println(
					"IMDB Rating for the movie: " + imdbData.getMovieName() + " is: " + imdbData.getMovieRating());
		}
	}
    
	/*
	 * Recursive function to get the imdb rating of the movie
	 */
	static ImdbData getRating(ImdbData imdbData) {

		if (imdbData.getCurrentPosition() <= 100 && imdbData.getMovieRating() == "") {
			Document rootDocument = getIMDBData(imdbData);
			for (Element element : rootDocument.select("div.list").select("div.info")) {
				String movieNameInImdb = element.select("a").get(0).text();
				//Removing the special characters in the movie name to compare
				movieNameInImdb = movieNameInImdb.replaceAll("[^a-zA-Z]+", "");
				String movieNameToGetRating = imdbData.getMovieName().replaceAll("[^a-zA-Z]+", "");
				/*Using strict comparison for the movie name we can use contains all function to retrieve 
				 * the movie name but more than movie name might be returned eg.. avenger 1 , avenger 2 etc..
				 * handling the above situation is out of scope for the application
				*/
				if (movieNameInImdb.toUpperCase().equalsIgnoreCase(movieNameToGetRating.toUpperCase())) {
					imdbData.setMovieRating(element.select("span.rating-rating").text());
				}
			}
			//Building the url to next page set of movie list
			imdbData = urlBuilder(imdbData);
			
			//Incrementing variable to track the page count list in url
			imdbData.setCurrentPosition(imdbData.getCurrentPosition() + 1);
			
			//recursive call to the method to find the movie name in the list if not found in the first set of page list
			imdbData = getRating(imdbData);

		}

		return imdbData;

	}
	/*
	 * Method to scrap the imdb site using Jsoup api
	 */
	static Document getIMDBData(ImdbData imdbData) {
		Document rootDocument = null;
		try {
			rootDocument = Jsoup.connect(imdbData.getImdbUrl()).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception in retrieving imdb information: " + e.getMessage());
		}
		return rootDocument;
	}
    
	/*
	 * Building the url for the consecutive pages to find the movie in the page navigated, imdb movie list
	 */
	static ImdbData urlBuilder(ImdbData imdbData) {
		String url = IMDB_URL;
		Integer nextPosition = (imdbData.getCurrentPosition() * 100) + 1;
		url = url + "?start=" + nextPosition + "&view=detail&sort=listorian:asc";
		imdbData.setImdbUrl(url);
		return imdbData;
	}
}
