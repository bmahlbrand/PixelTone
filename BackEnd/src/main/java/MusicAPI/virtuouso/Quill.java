package MusicAPI.virtuouso;

import ImageAPI.Params.MusicParams;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.rhythm.BeatDuration;
import MusicAPI.harmonicsKB.scale.ChromaticScale;
import MusicAPI.harmonicsKB.scale.DiatonicScale;
import MusicAPI.harmonicsKB.scale.PentatonicScale;
import MusicAPI.harmonicsKB.triads.*;
import MusicAPI.structure.*;
import MusicAPI.virtuouso.models.genetic.GeneticMotive;
import MusicAPI.virtuouso.models.genetic.GeneticSimpleComposition;

/**
 * Created by ben on 3/5/2016.
 *
 * The idea here is to create a class to interface the creation of a composition and its associated components with
 * the generative process
 *
 */
public class Quill {

    public static void createPhrase() {

    }

    public static void createComposition(MusicParams musicParams, String path) {
            GeneticSimpleComposition testComposition1, testComposition2;
            Mode mode = musicParams.RelativeMinor ? Mode.Ionian.relativeMinor() : Mode.Ionian;

    //        testComposition2 = new GeneticSimpleComposition(new Note(musicParams.Key1), mode, musicParams.TempoLow.getBpm());
            testComposition1 = new GeneticSimpleComposition(new Note(musicParams.Key2), mode, musicParams.TempoHigh.getBpm());

            MIDIGenerator.generateMidi(path, testComposition1.getGeneratedSong());

    //        MIDIGenerator.generateMidi(path, testComposition1.getGeneratedSong(), testComposition2.getGeneratedSong());
        }

    public static Section scaleExercise() {
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
        ChromaticScale cs = new ChromaticScale(new Note("C", BeatDuration.Quarter), Mode.Ionian);
        for (Note note : cs.getScale()) {
            //System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        DiatonicScale ds = new DiatonicScale(new Note("C", BeatDuration.Quarter), Mode.Ionian);
        for (Note note : ds.getScale()) {
            //System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        DiatonicScale ds2 = new DiatonicScale(new Note("C", BeatDuration.Quarter), Mode.Dorian);
        for (Note note : ds2.getScale()) {
            //System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        PentatonicScale ps = new PentatonicScale(new Note("C", BeatDuration.Eighth), Mode.Mixolydian);
        for (Note note : ps.getScale()) {
            //System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        ps = new PentatonicScale(new Note("C", BeatDuration.Eighth), Mode.Aeolian);
        for (Note note : ps.getScale()) {
            //System.out.println(note.toString());
            Beat thatBeat = new Beat();
            thatBeat.addNote(note);
            Measure measure = new Measure();
            measure.addBeat(thatBeat);
            thisSection.addMeasure(measure);
        }

        return thisSection;
    }
}
