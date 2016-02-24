package MusicAPI;

import static spark.Spark.*;
import MusicAPI.GeneticAlgorithm.*;

public class main {
	public static void main(String[] args) {

        get("/generateSong", (request, response) -> {
            return "Placeholder";
        });

        CommonChordProgFitFunc fitnessfunction = new CommonChordProgFitFunc();
        GeneticAlgorithm.geneticAlgorithm(1,7,100,fitnessfunction);

    }        
}