package ImageAPI;

import ImageAPI.Params.EmotionParamEntry;
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

    public MusicParams TranslateParameters(GenerationParams gp)
    {
        TranslatedParams translatedParams = new TranslatedParams(
                emotionTranslationFactory.translate(gp.numberOfFaces, gp.faces),
                colorTranslationFactory.translate(gp.colorEntries)
        );

        return MergeParams(translatedParams);
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

        //Calculate Tempo Range

        //Get array of tempos + weights

        //we dont need to sort it
        //for each entry
            //multiple tempo weight * percent
                //if tempo weight < 1, then make it 0-1+(1-tempoweight)
                //Add all calced together
            //multiple calced by default tempo (100)?
        //Gets base tempo
        //Get range
            //add percentages above 1, and < 1
                //whichever is larger, start with calced tempo then add 10 or -10


        //calculate tempo to use
        class TempoM
        {
            double tempo;
            double weight;

            public TempoM(double t, double w)
            {
                tempo = t;
                weight = w;

            }
        }

        TempoM[] tm;
        double TempoHigh;
        double  TempoLow;

        //Merge arrays
        tm = new TempoM[translatedParams.fromColor.length + translatedParams.fromColor.length];
        int i = translatedParams.fromColor.length - 1, j = translatedParams.fromEmotion.length, k = tm.length;

        //SORRY HAHA
        while(k > 0)
            tm[--k] =
                    (j < 0 || (i >= 0 && translatedParams.fromColor[i].Tempo >= translatedParams.fromEmotion[j].Tempo))
                            ? new TempoM(translatedParams.fromColor[i].Tempo, translatedParams.fromColor[i--].overallWeight)
                            : new TempoM(translatedParams.fromEmotion[j].Tempo, translatedParams.fromEmotion[j--].overallWeight);


        TempoHigh = TempoLow = tm[0].tempo;
        double calcedTempo = 100;
        double tempSum = 0;
        double range = 0;
        int weighter = 0;
        for (TempoM t: tm)
        {
            double temp;
            if(t.tempo < 1)
            {
                temp = -1 + (1 - t.tempo);
                if(TempoLow > t.tempo)
                    TempoLow = t.tempo;
                weighter--;
            }
            else
            {
                temp = t.tempo;
                if(TempoHigh < t.tempo)
                    TempoHigh = t.tempo;
                weighter++;
            }

            tempSum += temp * t.weight;

        }

        calcedTempo *= tempSum;




        String k1;
        String k2;
        boolean relativeMinor;

        /*
            Staccato, //last part of note should be silenced
    Staccatissimo, //shorter staccato
    Marcato, //loud as an accent, short as staccato
    Accent, //emphasized beginning and taper off quickly
    Tenuto, //multiple meanings, but lets assume "doo" in jazz articulation ie slight separation
    Unaccented;*/


        //Change this for sprint 3...



            //This is okay
            k1 = translatedParams.fromEmotion[0].key1;
            k2 = translatedParams.fromEmotion[0].key2;
            relativeMinor = !translatedParams.fromColor[0].major;


            //Calculate Accent Type, Dynamic Weights

            double AW = translatedParams.fromColor[0].AccentWeight;


            String AT;

            if(AW > 1.5)
                AT = ""
            double AWP = translatedParams.fromColor[0].overallWeight;



        //}




        MusicParams mp = new MusicParams(calcedTempo, calcedTempo + 10, k1, k2, relativeMinor, AW, AWP);

        return mp;
    }

}
































