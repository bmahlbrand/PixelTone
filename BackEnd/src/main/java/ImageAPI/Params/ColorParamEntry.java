package ImageAPI.Params;

/**
 * Created by Jacob on 3/27/2016.
 */
public class ColorParamEntry {

    double Tempo;
    double AccentWeight;
    double FlatOSharpWeight;
    boolean major;
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
