package ImageAPI.Objects;

import MusicAPI.harmonicsKB.rhythm.Tempo;

/**
 * Created by Jacob on 4/17/2016.
 */
public class ReturnParams {
    public String imageKey;
    public int numberOfFaces;
    //public String topEmotion;
    public String prominantColor;
    public String chosenKey;
    public String songPath;
    public String chosenTempo;
    public boolean relativeMinor;

    public ReturnParams(String ik, int n,  Colors c, String k, Tempo t, boolean r, String sp)
    {
        imageKey = ik;
        numberOfFaces = n;
       // topEmotion = e.toString();
        prominantColor = c.toString();
        chosenKey = k;
        chosenTempo = t.toString();
        relativeMinor = r;
        songPath = sp;
    }

}
