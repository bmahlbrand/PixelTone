package MusicAPI.virtuouso.models.markov;

import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.structure.Beat;
import MusicAPI.structure.Measure;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;
import MusicAPI.utils.Pair;

import java.io.File;
import java.util.*;

//import xml_parser.MXMLContentHandler;
//import xml_parser.MXMLWriter;


/**
 * Created by ben on 4/3/2015.
 */
public class ToneTransitionTable extends HiddenMarkovModel {


    public ToneTransitionTable(int order, Pair<String, String> keySig, char a) {
        this.order = order;
        this.lastKBeats = new LimitedQueue<>(order);
        this.markov = new LimitedQueue<>(order);

        int k = 0;
        while (k++ < order) //add k dimensions for model
            markov.add(new MarkovState(keySig, a));
    }

    //use the mxml parser and note/measure data objects...just as a quick way to sample other docs if we want to build a stronger model
    public void trainFile(File file) {
//        MetaData data = new MetaData(file);
//        ArrayList<ArrayList<Note>> beats = data.getBeatList();
//        trainPiece(beats);
    }

    //adds notes within phrase list to model and updates state
    public void trainPhrase(List<String> phrase) {
        for (String note : phrase)
            trainNote(note);
    }

    public void trainPiece(List<Beat> beats) {
        for (Beat beat : beats)
            trainBeat(beat);
    }


    public void trainPiece(ArrayList<ArrayList<Note>> beats) {
        int i = 0;
        for (ArrayList<Note> n : beats) {
            Beat beat = new Beat();
//            beat.setNotes(n);
            trainBeat(beat);
//            pickNote(beat, beat);
//            pickNote(beat, beat);
//            pickNote(beat, beat);
        }
    }

    public void trainMeasure(Measure measure) {
//        for (Beat beat : measure.getBeats())

    }

    public void trainBeat(Beat beat) {
        statesCounter++;

        for (Note n : beat.getNotes()) {
            if (lastKBeats.size() == 0) {
                lastKBeats.add(beat);
                return;
            }

            updateKOrderLayers(beat);
//            trainNote(n);
        }

        lastKBeats.add(beat);
    }

    public void trainNote(Note note) {
//        trainNote(BlackMagicka.noteIndexToString(markov.getFirst().getPitchAxis().noteIndex(note), markov.get(0).getPitchAxis().sharp)); //ugliest evar
    }

    //adds note to model and updates state
    public void trainNote(String currentPitch) {
//        updateKOrderLayers(currentPitch);
    }

    HashMap<Note, Double> getBeatLikelihoods(Beat beat, int k) {
        HashMap<Note, Double> distribution = new HashMap<>();

        for (Note n : beat.getNotes()) {
            distribution.put(n, getKthLikelihood(n, k));
        }

        return distribution;
    }

    private double getKthLikelihood(Note note, int k) { //k == index of order
        String lastNote = lastKBeats.get(k - 1).getNotes().get(0).toString();

//        int i = BlackMagicka.noteIndex(note.toString(), markov.get(0).getPitchAxis().sharp);
//        int j = BlackMagicka.noteIndex(lastNote, markov.get(0).getPitchAxis().sharp);
//        if (i < 0 || i > 11 || j < 0 || j > 11)
//            return -1.0;

//        return markov.get(k - 1).getIndexLikeliness(i, j);
        return 0.0;
    }


    //convert tone distribution to degree for decision
    public HashMap<Degree, Double> possibleNotestoDegree(HashMap<Note, Double> toneDist) {
        HashMap<Degree, Double> degreeDist = new HashMap<>();
        Iterator it = toneDist.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            degreeDist.put(getDegree((Note) pair.getKey()), 1.0);
        }

        return degreeDist;
    }

    public HashMap<Note, Double> generateBeatChoices(Beat beat) {
        //getdegree from beats
//        lastKBeats.getFirst();
        HashMap<Note, Double> distribution = getBeatLikelihoods(beat, 1);
//        System.out.println(beat.getNotes() + ": has likelihood of = " + getBeatLikelihoods(beat, 0).keySet() + getBeatLikelihoods(beat, 0).values());
        return distribution;
    }

    public Degree getDegree(Note note) {
        return markov.getFirst().getPitchAxis().degreeIndex(note);
    }
}