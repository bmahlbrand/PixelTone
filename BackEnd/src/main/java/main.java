package hello;

import static spark.Spark.*;

public class main {
	public static void main(String[] args) {
        get("/generateSong", (request, response) -> {
        	//insert song generation method here (or whichever method returns the song link)
        }
    }
}