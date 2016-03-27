package ImageAPI.Objects;

import MusicAPI.harmonicsKB.Tempo;

/**
 * Created by Jacob on 3/26/2016.
 */
public class FromEmotion {

    public Tempo tempo;
    public String key;
    public EmotionWeights ew;

    public FromEmotion(Tempo tempo, String key, EmotionWeights ew)
    {
        this.tempo = tempo;
        this.key = key;
        this.ew = ew;

    }



}
