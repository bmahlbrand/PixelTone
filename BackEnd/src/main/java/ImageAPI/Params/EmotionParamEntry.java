package ImageAPI.Params;

/**
 * Created by Jacob on 3/27/2016.
 */
public class EmotionParamEntry {
    public String key1;
    public String key2;
    public double Tempo;
    double AccentWeight;
    double FlatOSharpWeight;
    public double overallWeight;

    public EmotionParamEntry(String k, String k2, double t, double w) {
        this.key1 = k;
        this.key2 = k2;
        this.Tempo = t;
        this.AccentWeight = w;
        //this.FlatOSharpWeight = f;
    }
}
