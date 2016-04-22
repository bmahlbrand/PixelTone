import static spark.Spark.*;
import ImageAPI.Objects.ReturnParams;
import ImageAPI.Params.GenerationParams;
import ImageAPI.Params.MusicParams;
import MusicAPI.harmonicsKB.rhythm.Tempo;
import com.google.gson.Gson;
import MusicAPI.virtuouso.*;
import ImageAPI.*;
import java.io.*;
import java.nio.file.Files;

public class main {

    public static MoodToMusicFactory moodToMusicFactory = new MoodToMusicFactory();

    public static void main(String[] args) {

        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");

        get("/notes/:filename", (request, response) -> {
          String filename = "./songs/" + request.params(":filename");
          File file = new File(filename);
          if(file.exists()) {
            OutputStream os = response.raw().getOutputStream();
            Files.copy(file.toPath(), os);
            os.close();
            return null;
          }else{
            return "failure";
          }
        });

        get("/songs/:filename", (request, response) -> {
          String filename = "./songs/" + request.params(":filename");
          File file = new File(filename);
          if(file.exists()) {
            OutputStream os = response.raw().getOutputStream();
            Files.copy(file.toPath(), os);
            os.close();
            return null;
          }else{
            return "failure";
          }
        });


        post("/generateSong", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            Gson gson = new Gson();
            String json = gson.toJson(handleParameters(request.body()));

            System.out.println("Generation Complete!");
            return json;
        });
    }

    public static ReturnParams handleParameters(String params) throws Exception {

        try {
            Gson gson = new Gson();
            System.out.println(params);
            GenerationParams gp = gson.fromJson(params, GenerationParams.class);

            System.out.println("Translating Parameters into Musical Params...");
            MusicParams mp = moodToMusicFactory.TranslateParameters(gp);

            String path = "./songs/" + gp.imageKey.replaceAll("\\\\", "\\\\\\\\") + ".mid";
            displayMP(mp);
            System.out.println("Generating Composition from Musical Params...");
            Quill.createComposition(mp, path);

            String imageKey = gp.imageKey;
            String songpath = imageKey + ".mid";
            String np =  songpath + ".NTS";

            ReturnParams rp = new ReturnParams(gp.imageKey, gp.numberOfFaces, gp.colorEntries.get(0).Color, mp.Key1, mp.TempoLow,mp.RelativeMinor, songpath, np );

            return rp;
        } catch (Exception e) {
            throw new Exception("Exception", e);
        }
    }

    private static void testMidiGeneration() {
              MusicParams mp = new MusicParams(Tempo.Largo, Tempo.Moderato, "Bb", "C", true);
              Quill.createComposition(mp, "./songs/testmidi.mid");
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
