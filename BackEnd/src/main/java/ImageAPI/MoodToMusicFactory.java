package ImageAPI;

import ImageAPI.Objects.GenerationParams;
import ImageAPI.Objects.TranslatedParams;

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
        TranslatedParams translatedParams = new TranslatedParams();

        //TranslatedParams.fromColor = colorTranslationFactory.translate(gp.faces, gp.numberOfFaces);
        //TranslatedParams.fromEmotion = emotionTranslationFactory.translate(gp.ColorEntries);


        return translatedParams;
    }

}
