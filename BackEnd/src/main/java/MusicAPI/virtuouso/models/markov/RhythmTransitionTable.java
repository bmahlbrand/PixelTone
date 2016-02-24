package MusicAPI.virtuouso.models.markov;

import MusicAPI.utils.LimitedQueue;
import MusicAPI.utils.MersenneTwisterFast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ben on 4/4/2015.
 */
public class RhythmTransitionTable {
    private MersenneTwisterFast rand = new MersenneTwisterFast(); //rng

    private int order;
    private int statesCounter = 0;

    private LinkedList<String> lastKnotes; //last K order notes
    private LinkedList<MarkovState> markov; //list of the K order markov states

    public RhythmTransitionTable(int order) {
        this.order = order;
        this.lastKnotes = new LimitedQueue<>(order);
        this.markov = new LimitedQueue<>(order);

//        int k = 0;
//        while (k++ < order) //add k dimensions for model
//            markov.add(new MarkovState());
    }

    //use the mxml parser and data objects...just as a quick way to sample other docs if we want to build a stronger model
    public void trainFile() {

    }

    //adds notes within phrase list to model and updates state
    public void trainPhrase(List<String> phrase) {
        for (String note : phrase)
            trainNote(note);
    }

    //adds note to model and updates state
    public void trainNote(String currentPitch) {
        statesCounter++;

        if (lastKnotes.size() == 0) {
            lastKnotes.add(currentPitch);
            return;
        }

        updateKOrderLayers(currentPitch);
        lastKnotes.add(currentPitch);
    }

    public String pickNote() {
        return null;
    }

    private void updateKOrderLayers(String currentPitch) {
//        for (int i = 0; i < lastKnotes.size(); i++)
//            markov.get(i).updateLayer(currentPitch, lastKnotes.get(i));
    }
}
