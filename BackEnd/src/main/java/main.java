import static spark.Spark.*;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Objects.Emotion;
import ImageAPI.Objects.Face;

import ImageAPI.Objects.ReturnParams;
import ImageAPI.Params.GenerationParams;

import ImageAPI.Params.MusicParams;

import MusicAPI.harmonicsKB.dynamics.Accent;
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

            Gson gson = new Gson();

            Composition comp = Quill.createCompositionOLD();

            String json = gson.toJson(comp);
            return json;
        });


        post("/generateSongP", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            Gson gson = new Gson();
            String json = gson.toJson(handleParameters(request.body()));
            
            System.out.println("Generation Complete!");
            return json;
        });

        testMidiGeneration();

    }

    public static ReturnParams handleParameters(String params) throws Exception {

        try {
            Gson gson = new Gson();
            GenerationParams gp = gson.fromJson(params, GenerationParams.class);
            //System.out.println(params);

          //  System.out.println("Parameters Received");
         ///   System.out.println("------------------------------------");
          //  System.out.println("Facial Information");
         //   System.out.println("Number of Faces:" + gp.numberOfFaces);
            int i = 0;
          /*  for (Face f : gp.faces) {

                System.out.println("Face #" + i++ + ":\n");

              //  for (Emotion e : f.emotions) {
                    System.out.println("Facial Emotion:" + f.emotions.get(0).emotion);
                    System.out.println("Emotion Value:" + f.emotions.get(0).value);
              //  }

                System.out.println("------------------------------------");
            }

            System.out.println("\nImage Color Information\n");

            for (ColorEntry ce : gp.colorEntries) {
                System.out.println("Color:" + ce.Color + " Color Percent:" + ce.Percent);
            }
*/
            System.out.println("Translating Parameters into Musical Params...");
            MusicParams mp = moodToMusicFactory.TranslateParameters(gp);
            String path = "./songs/" + gp.imageKey.replaceAll("\\\\", "\\\\\\\\") + ".mid";
            displayMP(mp);
            System.out.println("Generating Composition from Musical Params...");
            Quill.createComposition(mp, path);

            //return gp.imageKey.replaceAll("\\\\","\\\\\\\\");

            String imageKey = gp.imageKey;
            String songpath = imageKey + ".mid";

            ReturnParams rp = new ReturnParams(gp.imageKey, gp.numberOfFaces, gp.colorEntries.get(0).Color, mp.Key1, mp.TempoLow,mp.RelativeMinor, songpath );

            return rp;
        } catch (Exception e) {
            throw new Exception("Exception", e);
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

    private static void displayMP(MusicParams mp)
    {

        System.out.println(mp.TempoLow);
        System.out.println(mp.TempoHigh);
        System.out.println(mp.Key1);
        System.out.println(mp.Key2);
        System.out.println(mp.RelativeMinor);
        System.out.println(mp.AccentType1);
        System.out.println(mp.AccentWeight1);
        System.out.println(mp.AccentType2);
        System.out.println(mp.AccentWeight2);

        System.out.println(mp.numberOfVoices);
        System.out.println(mp.chaosLevel);


    }

}
