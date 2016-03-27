package ImageAPI;

import ImageAPI.Params.GenerationParams;
import ImageAPI.Params.TranslatedParams;

/**
 * Created by Jacob on 2/22/2016.
 */
public class MoodToMusicFactory {

    private ColorTranslationFactory colorTranslationFactory;
    private EmotionTranslationFactory emotionTranslationFactory;

    public MoodToMusicFactory()
    {
        colorTranslationFactory = new ColorTranslationFactory();
        emotionTranslationFactory = new EmotionTranslationFactory();
    }

    //HRMMM what shall we return??
    public TranslatedParams TranslateParameters(GenerationParams gp)
    {
        TranslatedParams translatedParams = new TranslatedParams(
                emotionTranslationFactory.translate(gp.numberOfFaces, gp.faces),
                colorTranslationFactory.translate(gp.colorEntries)
        );

        return translatedParams;
    }

    private MusicParams MergeParams(TranslatedParams translatedParams)
    {


        return MusicParams;
    }

}
