package ImageAPI;

import ImageAPI.Params.GenerationParams;
import ImageAPI.Params.MusicParams;
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
        //From faces we have
            //Weight of this emotion compared to others
            // Two Keys
            // Tempo
            // Accent Weight (volume?),  > 1 = more  accent, < 1 = more tenuto
                //Maybe change this later to be a certain type (staccato, marcato, accent, tenuto)

        //From Colors we have
            //Overall color percent
            //Tempo
            //AccentWeight
            //boolean Major(true)/minor
            //Flat or Sharp weight  > 1 = more  sharp, < 1 = more flat





        //Both
            //Tempo modifier > 1 = faster
            //Accent Weight
        //Faces
            //Key
            //Percent of this emotion
        //Color
            //Major/Minor
            //Flat or Sharp Modifier
            //Percent of this color


        //Thoughts
            //currently accent weight is more like LOUDNESS, change color to LOUDNESS, and change emotion to accent mark weighting??

        return null;
    }

}
