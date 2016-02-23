package PixelTone;

import com.google.gson.Gson;

import static spark.Spark.*;

public class main {
	public static void main(String[] args) {
        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");

        post("/generateSong", (request, response) ->
        {
            System.out.println("Generate Parameter Request Received");
            return handleParameters(request.body());
        });
    }

    public static String handleParameters(String params) throws Exception
    {
        try{
            Gson gson = new Gson();
            GenerationParams gp = gson.fromJson(params, GenerationParams.class);
            System.out.println(params);

            //Ship to MoodToMusic to change the moods/emotions to music
            //Store song in database?
            //????? TBD

        }
        catch (Exception e)
        {
            throw new Exception("Invalid JSON", e);
        }
        //Ideally we want to return a status code based on processing status (200) for success
        return "Sucessfully Processed Params";
    }
}