package ImageAPI.Params;

/**
 * Created by Jacob on 3/27/2016.
 */
public class ColorParamEntry {

    public double Tempo;
    public double AccentWeight;
    public double FlatOSharpWeight;
    public boolean major;
    public double overallWeight;

    //Tempo, Accent, Flat, Major
    public ColorParamEntry(double t, double w, double f, boolean major)
    {
        this.Tempo = t;
        this.AccentWeight = w;
        this.FlatOSharpWeight = f;
        this.major = major;
    }
}
