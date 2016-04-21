package ImageAPI.Params;

import MusicAPI.harmonicsKB.dynamics.Accent;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.rhythm.Tempo;

/**
 * Created by Jacob on 3/27/2016.
 */
public class MusicParams {

    public Tempo TempoLow;
    public Tempo TempoHigh;
    public String Key1;
    public String Key2;
    public boolean RelativeMinor;
    public Accent AccentType1;
    public double AccentWeight1;
    public Accent AccentType2;
    public double AccentWeight2;

    public int numberOfVoices;
    public int chaosLevel;



    public MusicParams(Tempo tl, Tempo th, String k1, String k2, boolean rm, Accent at1, double aw1, Accent at2, double aw2, int chaos, int voices) {
        TempoLow = tl;
        TempoHigh = th;
        Key1 = k1;
        Key2 = k2;
        RelativeMinor = rm;
        AccentType1 = at1;
        AccentType2 = at2;
        AccentWeight1 = aw1;
        AccentWeight2 = aw2;
        numberOfVoices = voices;
        chaosLevel = chaos;
    }

    public MusicParams(Tempo tl, Tempo th, String k1, String k2, boolean rm) {
        TempoLow = tl;
        TempoHigh = th;
        Key1 = k1;
        Key2 = k2;
        RelativeMinor = rm;
    }

}
