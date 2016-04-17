package ImageAPI;

import ImageAPI.Params.GenerationParams;
import ImageAPI.Params.MusicParams;
import ImageAPI.Params.TranslatedParams;
import MusicAPI.harmonicsKB.dynamics.Accent;
import MusicAPI.harmonicsKB.rhythm.Tempo;

/**
 * Created by Jacob on 2/22/2016.
 */
public class MoodToMusicFactory {


    private class TempoM {
        double tempo;
        double weight;

        public TempoM(double t, double w) {
            tempo = t;
            weight = w;
        }
    }


    private ColorTranslationFactory colorTranslationFactory;
    private EmotionTranslationFactory emotionTranslationFactory;

    public MoodToMusicFactory() {
        colorTranslationFactory = new ColorTranslationFactory();
        emotionTranslationFactory = new EmotionTranslationFactory();
    }

    public MusicParams TranslateParameters(GenerationParams gp) {
        TranslatedParams translatedParams = new TranslatedParams(
                emotionTranslationFactory.translate(gp.numberOfFaces, gp.faces),
                colorTranslationFactory.translate(gp.colorEntries)
        );

        return MergeParams(translatedParams, gp.chaos, gp.voices, gp.prefs);
    }

    private MusicParams MergeParams(TranslatedParams translatedParams,int chaos, int voices, int prefs) {
        //From faces we have
        //Weight of this emotion compared to others
        // Two Keys
        // Tempo
        // Accent Weight (volume?),  > 1 = more  accent, < 1 = more tenuto

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
        //calculate tempo to use
        TempoM[] tm;
        double TempoHigh;
        double TempoLow;

        //Merge arrays
        tm = new TempoM[translatedParams.fromColor.length + translatedParams.fromEmotion.length];
        int i = translatedParams.fromColor.length - 1, j = translatedParams.fromEmotion.length - 1, k = tm.length;

        //SORRY HAHA
        while (k > 0)
            tm[--k] =
                    (j < 0 || (i >= 0 && translatedParams.fromColor[i].Tempo >= translatedParams.fromEmotion[j].Tempo))
                            ? new TempoM(translatedParams.fromColor[i].Tempo, translatedParams.fromColor[i--].overallWeight)
                            : new TempoM(translatedParams.fromEmotion[j].Tempo, translatedParams.fromEmotion[j--].overallWeight);

        TempoHigh = TempoLow = tm[0].tempo;
        double calcedTempo = 100;
        double tempSum = 0;

        //Loop through all tempos,
        for (TempoM t : tm) {
            double temp;
            if (t.tempo < 1) { //if < 1, get difference from 1 and negify it
                temp = -1 + (1 - t.tempo);
                if (TempoLow > t.tempo) {
                    TempoLow = t.tempo;
                }
            } else {            //if > just use it
                temp = t.tempo;
                if (TempoHigh < t.tempo) {
                    TempoHigh = t.tempo;
                }

            }
            //Cumulative weight to modify tempo.
            tempSum += temp * t.weight;
        }
        //Modify 100 by the weighted sum of tempo modifiers.
        //Check this... may never be < 50 > 150, not sure
        calcedTempo *= tempSum;


        String k1;
        String k2;
        boolean relativeMinor;

        //Change this for sprint 3...
        //This is okay
        k1 = translatedParams.fromEmotion[0].key1;
        k2 = translatedParams.fromEmotion[0].key2;
        relativeMinor = !translatedParams.fromColor[0].major;


        //Calculate Accent Type, Accent Weight
        //Use top 2 colors
        Accent AT1 = translatedParams.fromColor[0].AccentType;
        Accent AT2 = translatedParams.fromColor[1].AccentType;

        //total percent
        double t = translatedParams.fromColor[0].overallWeight + translatedParams.fromColor[1].overallWeight;
        double w1 = translatedParams.fromColor[0].overallWeight / t;
        //double w2 = translatedParams.fromColor[1].overallWeight/t;

        double AW1 = 1;
        double AW2 = 1;
        if (w1 >= .75) {
            AW1 = translatedParams.fromColor[0].AccentPercent;
            AW2 = translatedParams.fromColor[1].AccentPercent / 2;
        } else if (w1 >= .40 && w1 <= .60) {
            AW1 = translatedParams.fromColor[0].AccentPercent;
            AW2 = translatedParams.fromColor[1].AccentPercent;
        } else if (w1 <= .25) {
            AW1 = translatedParams.fromColor[0].AccentPercent / 2;
            AW2 = translatedParams.fromColor[1].AccentPercent;
        }

        Tempo t1 = Tempo.Moderato.getTempo((int) calcedTempo);
        Tempo t2 = Tempo.Moderato.getTempo((int) (calcedTempo + 10));

        MusicParams mp = new MusicParams(t1, t2, k1, k2, relativeMinor, AT1, AW1, AT2, AW2, chaos, voices);

        return mp;
    }

}