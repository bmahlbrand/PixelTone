package MusicAPI.virtuouso.models;

import MusicAPI.structure.Beat;
import MusicAPI.utils.MersenneTwisterFast;

import java.util.LinkedList;

/**
 * Created by ben on 2/6/2016.
 */
public abstract class HiddenMarkovModel {
    private MersenneTwisterFast rand = new MersenneTwisterFast();

    protected int order;
    protected int statesCounter = 0;

    protected LinkedList<Beat> lastKBeats; //last K order notes
    protected LinkedList<MarkovState> markov; //list of the K order markov states


    protected void updateKOrderLayers(Beat beat) {
        for (int i = 0; i < lastKBeats.size(); i++)
            markov.get(i).updateLayer(beat, lastKBeats.get(i));
    }

    public int getRand(int i) {
        return rand.nextInt(i);
    }

    public void printHistogram() {
        for (int k = 0; k < order; k++) {
            System.out.println("-----------------------------------------");
            System.out.println("              order: " + (k + 1) + "               ");
            markov.get(k).printStateHistogram();
            System.out.println("-----------------------------------------");
        }
    }

    public void printProbMatrix() {
        for (int k = 0; k < order; k++) {
            System.out.println("-----------------------------------------");
            System.out.println("              order: " + (k + 1) + "               ");
            markov.get(k).printStateProbMatrix();
            System.out.println("-----------------------------------------");
        }
    }
}