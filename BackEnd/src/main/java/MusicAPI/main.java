package MusicAPI;

import static spark.Spark.*;

public class main {
	public static void main(String[] args) {

        get("/generateSong", (request, response) -> {
            return "Placeholder";
        });


    }        
}