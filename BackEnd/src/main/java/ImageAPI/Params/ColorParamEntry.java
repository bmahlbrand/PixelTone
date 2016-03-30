package ImageAPI.Params;

import MusicAPI.harmonicsKB.dynamics.Accent;

/**
 * Created by Jacob on 3/27/2016.
 */
public class ColorParamEntry {

    public double Tempo;
    public Accent AccentType;
    public double AccentPercent;
    public boolean major;
    public double overallWeight;

    //Tempo, Accent, Flat, Major
    public ColorParamEntry(double t, Accent w, double f, boolean major)
    {
        this.Tempo = t;
        this.AccentType = w;
        this.AccentPercent = f;
        this.major = major;
    }
}
