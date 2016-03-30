package MusicAPI.virtuouso;

import MusicAPI.harmonicsKB.rhythm.BeatDuration;
import MusicAPI.harmonicsKB.triads.Augmented7thTriad;
import MusicAPI.harmonicsKB.triads.Diminished7thTriad;
import MusicAPI.harmonicsKB.triads.Major7ThTriad;
import MusicAPI.harmonicsKB.triads.Minor7ThTriad;
import MusicAPI.structure.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.*;

/**
 * Created by ben on 3/29/2016.
 */
public class JsonSerializerFactory {
    FileWriter fileWriter;

    static public void serializeComposition(Composition composition) throws IOException {

        FileOutputStream outputStream = new FileOutputStream("composition.json");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.setIndent("  ");

        Gson gson = new Gson();
        gson.toJson(composition, Composition.class, writer);

        writer.close();

    }

    public static void main(String []srgs) throws IOException {
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
        FileOutputStream outputStream = new FileOutputStream("composition.json");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.setIndent("  ");

        Gson gson = new Gson();
        gson.toJson(thisComposition, Composition.class, writer);

        writer.close();

        System.out.println();

    }
}
