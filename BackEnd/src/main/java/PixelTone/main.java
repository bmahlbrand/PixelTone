package PixelTone;

import static spark.Spark.*;

public class main {
	public static void main(String[] args) {
        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");
        post("/generateSong", (request, response) ->
        {
            //insert song generation method here (or whichever method returns the song link)
            System.out.println("Generate Parameter Request Received");
            //System.out.println(request.body());

            return "Received";
        });

    }

    //This will need to be a response??
    ///return http response
   public static String handleParameters(String params)
    {


        return "Hello World";
    }



}