import static spark.Spark.*;
import com.google.gson.Gson;
import MusicAPI.GeneticAlgorithm.*;

import ImageAPI.*;

public class main {
	public static void main(String[] args) {
        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");

        CommonChordProgFitFunc fitnessfunction = new CommonChordProgFitFunc();
        GeneticAlgorithm.geneticAlgorithm(1, 7, 100, fitnessfunction);

        post("/generateSong", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            return handleParameters(request.body());
        });
    }

    public static String handleParameters(String params) throws Exception {
        try {
            Gson gson = new Gson();
            GenerationParams gp = gson.fromJson(params, GenerationParams.class);
            //System.out.println(params);

            System.out.println("Parameters Received");
            System.out.println("------------------------------------");
            System.out.println("Facial Information");
            System.out.println("Number of Faces:" + gp.numberOfFaces);
            int i = 0;
            for(Face f : gp.faces) {
                
                System.out.println("Face #" + i++ + ":\n");
                
                for (Emotion e : f.emotions) {
                    System.out.println("Facial Emotion:" + e.emotion);
                    System.out.println("Emotion Value:" + e.value);
                }

                System.out.println("------------------------------------");
            }

            System.out.println("\nImage Color Information\n");
            System.out.println("Dominant Color:" + gp.domColor);
            System.out.println("#1 Palette Color:" + gp.pal1);
            System.out.println("#2 Palette Color:" + gp.pal2);
            System.out.println("#3 Palette Color:" + gp.pal3);
            //Ship to MoodToMusic to change the moods/emotions to music
            //Store song in database?
            //????? TBD

        } catch (Exception e) {
            throw new Exception("Invalid JSON", e);
        }
        //Ideally we want to return a status code based on processing status (200) for success
        return "Sucessfully Processed Params";
    }
}