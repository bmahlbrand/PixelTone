package ImageAPI.Params;

/**
 * Created by Jacob on 3/27/2016.
 */
public class MusicParams {

    double TempoLow;
    double TempoHigh;
    String Key1;
    String Key2;
    boolean RelativeMinor;
    double AccentType1;
    double AccentWeight1;
    double AccentType2;
    double AccentWeight2;
    double Dynamic1;
    double DynamicWeight1;
    double Dynamic2;
    double DynamicWeight2;

    public MusicParams(double tl, double th, String k1, String k2, boolean rm, double at1, double aw1, double at2, double aw2, double d1, double dw1, double d2, double dw2)
    {
        TempoLow = tl;
        TempoHigh = th;

    }

    
}
