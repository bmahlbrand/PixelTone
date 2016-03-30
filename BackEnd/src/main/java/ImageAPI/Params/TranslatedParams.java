package ImageAPI.Params;

/**
 * Created by Jacob on 3/26/2016.
 */
public class TranslatedParams {

    public EmotionParamEntry[] fromEmotion;
    public ColorParamEntry[] fromColor;

    public TranslatedParams(EmotionParamEntry[] fe, ColorParamEntry[] fc)
    {
        this.fromEmotion = fe;
        this.fromColor = fc;
    }
}
