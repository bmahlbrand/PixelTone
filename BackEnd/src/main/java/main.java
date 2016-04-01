import static spark.Spark.*;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Objects.Emotion;
import ImageAPI.Objects.Face;

import ImageAPI.Params.GenerationParams;

import ImageAPI.Params.MusicParams;

import MusicAPI.harmonicsKB.rhythm.Tempo;
import com.google.gson.Gson;

import MusicAPI.virtuouso.*;
import MusicAPI.structure.*;


import ImageAPI.*;

public class main {

    public static MoodToMusicFactory moodToMusicFactory = new MoodToMusicFactory();

    public static void main(String[] args) {

        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");

       // CommonChordProgFitFunc fitnessfunction = new CommonChordProgFitFunc();
       // GeneticAlgorithm.geneticAlgorithm(1, 7, 100, fitnessfunction);

        post("/generateSong", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            String status = handleParameters(request.body());
            Composition comp = Quill.createComposition();

            //Fake json this

            Gson gson = new Gson();
            String json = gson.toJson(comp);
            return json;
        });


        post("/generateSongP", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            String status = handleParameters(request.body());
            //Composition comp = Quill.createComposition();


            String imageKey = handleParameters(request.body());
            String songpath = imageKey + ".mid";

            //Fake json this
            String JSON = "{\"imageKey\":\"" + imageKey + "\",\"songPath\":\"" + songpath + "\"}";
            //Fake json this


            return JSON;
        });

        testMidiGeneration();

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
            for (Face f : gp.faces) {

                System.out.println("Face #" + i++ + ":\n");

                for (Emotion e : f.emotions) {
                    System.out.println("Facial Emotion:" + e.emotion);
                    System.out.println("Emotion Value:" + e.value);
                }

                System.out.println("------------------------------------");
            }

            System.out.println("\nImage Color Information\n");

            for( ColorEntry ce : gp.colorEntries) {
                System.out.println("Color:" + ce.Color + " Color Percent:" + ce.Percent);
            }

            MusicParams mp = moodToMusicFactory.TranslateParameters(gp);
            String path =  "./songs/" + gp.imageKey.replaceAll("\\\\","\\\\\\\\") + ".mid";
            Quill.createComposition(mp, path);

            return gp.imageKey.replaceAll("\\\\","\\\\\\\\");
        } catch (Exception e) {
            throw new Exception("Invalid JSON", e);
        }
        //Ideally we want to return a status code based on processing status (200) for success

    }

    private static void testMidiGeneration() {

//        GeneticSimpleComposition testComposition = new GeneticSimpleComposition();

//        MIDIGenerator.generateMidi(testComposition.getGeneratedSong());

        MusicParams mp = new MusicParams(Tempo.Largo, Tempo.Moderato, "Bb", "C", true);
        Quill.createComposition(mp, "./songs/testmidi.mid");
        System.out.println("song generated");

    }
}
