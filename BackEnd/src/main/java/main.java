import static spark.Spark.*;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Objects.Emotion;
import ImageAPI.Objects.Face;

import ImageAPI.Params.GenerationParams;

import MusicAPI.harmonicsKB.triads.Augmented7thTriad;
import MusicAPI.harmonicsKB.triads.Diminished7thTriad;
import MusicAPI.harmonicsKB.triads.Major7ThTriad;
import MusicAPI.harmonicsKB.triads.Minor7ThTriad;
import com.google.gson.Gson;
import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.rhythm.*;
import MusicAPI.virtuouso.*;

import ImageAPI.*;

public class main {

    public static MoodToMusicFactory moodToMusicFactory = new MoodToMusicFactory();

    public static void main(String[] args) {

        port(3001);

        get("/", (request, response) -> "PixelTone BackEnd Works");

        //CommonChordProgFitFunc fitnessfunction = new CommonChordProgFitFunc();
        //GeneticAlgorithm.geneticAlgorithm(1, 7, 100, fitnessfunction);

        post("/generateSong", (request, response) -> {
            System.out.println("Generate Parameter Request Received");

            return handleParameters(request.body());
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

            for( ColorEntry ce : gp.colorEntries)
            {
                System.out.println("Color:" + ce.Color + " Color Percent:" + ce.Percent);
            }

            moodToMusicFactory.TranslateParameters(gp);


            //Store song in database?
            //????? TBD

        } catch (Exception e) {
            throw new Exception("Invalid JSON", e);
        }
        //Ideally we want to return a status code based on processing status (200) for success
        return "Sucessfully Processed Params";
    }

    private static void testMidiGeneration(){
        Note quarterNote = new Note("A", BeatDuration.Quarter);
        Note halfNote = new Note("B", BeatDuration.Half);
        Beat thisBeat = new Beat();
        thisBeat.addNote(quarterNote);
        thisBeat.addNote(halfNote);
        Measure thisMeasure = new Measure();
        thisMeasure.addBeat(thisBeat);
        Section thisSection = new Section();
        thisSection.addMeasure(thisMeasure);

        Beat b = new Beat();
        Chord chord = new Chord(new Diminished7thTriad(new Note("C")),BeatDuration.Half);
        b.addChord(chord);
        Measure m = new Measure();
        m.addBeat(b);
        //thisSection.addMeasure(m);

        Beat b2 = new Beat();
        Chord chord2 = new Chord(new Major7ThTriad(new Note("C")),BeatDuration.Half);
        b2.addChord(chord2);
        m.addBeat(b2);
        //thisSection.addMeasure(m);

        Beat b3 = new Beat();
        Chord chord3 = new Chord(new Minor7ThTriad(new Note("A")),BeatDuration.Half);
        b3.addChord(chord3);
        m.addBeat(b);
        //thisSection.addMeasure(m);
        Beat b5 = new Beat();
        Rest rest = new Rest(BeatDuration.Whole);
        b5.addRest(rest);
        Measure m2 = new Measure();
        m2.addBeat(b5);

        Beat b4 = new Beat();
        Chord chord4 = new Chord(new Augmented7thTriad(new Note("C")),BeatDuration.Half); //look into correctness of this one
        b4.addChord(chord4);
        m2.addBeat(b);
        thisSection.addMeasure(m2);

//        ChromaticScale cs = new ChromaticScale(new Note("C", BeatDuration.Whole), Mode.Ionian);
//        for (Note note : cs.getScale()) {
//            System.out.println(note.toString());
//            Beat thatBeat = new Beat();
//            thatBeat.addNote(note);
//            Measure measure = new Measure();
//            measure.addBeat(thatBeat);
//            thisSection.addMeasure(measure);
//        }
/*
        DiatonicScale ds = new DiatonicScale(new Note("C", BeatDuration.Quarter), Mode.Ionian);
        for (Note note : ds.getScale()) {
            System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        PentatonicScale ps = new PentatonicScale(new Note("C", BeatDuration.Eighth), Mode.Mixolydian);
        for (Note note : ps.getScale()) {
            System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        ps = new PentatonicScale(new Note("C", BeatDuration.Eighth), Mode.Aeolian);
        for (Note note : ps.getScale()) {
            System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }
*/
        Voice thisVoice = new Voice();
        thisVoice.addSection(thisSection);
        Composition thisComposition = new Composition(120);
        thisComposition.addVoice(thisVoice);

        MIDIGenerator.generateMidi(thisComposition);


    }
}