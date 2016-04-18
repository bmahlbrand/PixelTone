package ImageAPI;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Params.*;
import MusicAPI.harmonicsKB.dynamics.Accent;
import MusicAPI.harmonicsKB.rhythm.Tempo;

/**
 * Created by Jacob on 2/22/2016.
 */
public class MoodToMusicFactory {


    private class TempoM {
        double tempo;
        double weight;
        boolean color;

        public TempoM(double t, double w, boolean c) {
            tempo = t;
            weight = w;
            color = c;
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


        //Convert pref slider into weight modifier
        boolean color = false;
        if(prefs >= 5)
            color = true;

        double modWeight = (double) prefs / 5 ;
        modWeight = 1 + Math.abs(modWeight - 1);
        double calcedTempo = 100;
        double tempSum = 0;



        //Get pre-pref-weighted total weight
        double prePrefWeight = 0;
        double comboWeight = 0;

        //Normalize color weights
        for(ColorParamEntry c: translatedParams.fromColor )
            prePrefWeight += c.overallWeight;

        comboWeight += prePrefWeight;

        //Recalibrate
        for(ColorParamEntry c: translatedParams.fromColor )
            c.overallWeight /= prePrefWeight;

        prePrefWeight = 0;

        //Normalize emotion weights
        for(EmotionParamEntry c: translatedParams.fromEmotion )
            prePrefWeight += c.overallWeight;

        for(EmotionParamEntry c: translatedParams.fromEmotion )
            c.overallWeight /= prePrefWeight;

        comboWeight += prePrefWeight;

        //Normalize all weights based on combo weight

        if(color) {
            for (ColorParamEntry c : translatedParams.fromColor)
                c.overallWeight = c.overallWeight * modWeight / comboWeight;

            for (EmotionParamEntry c : translatedParams.fromEmotion)
                c.overallWeight = c.overallWeight  / comboWeight;
        }
        else {
            for (ColorParamEntry c : translatedParams.fromColor)
                c.overallWeight = c.overallWeight  / comboWeight;

            for (EmotionParamEntry c : translatedParams.fromEmotion)
                c.overallWeight = c.overallWeight * modWeight / comboWeight;
        }


        prePrefWeight = 0;
        comboWeight = 0;


        //get total weight after modweight
        for(ColorParamEntry c: translatedParams.fromColor )
            prePrefWeight += c.overallWeight;

        for(EmotionParamEntry c: translatedParams.fromEmotion )
            prePrefWeight += c.overallWeight;

        for (ColorParamEntry c : translatedParams.fromColor)
            c.overallWeight = c.overallWeight  /  prePrefWeight;

        for (EmotionParamEntry c : translatedParams.fromEmotion)
            c.overallWeight = c.overallWeight  /  prePrefWeight;


            //SORRY HAHA
            while (k > 0)
                tm[--k] =
                        (j < 0 || (i >= 0 && translatedParams.fromColor[i].Tempo >= translatedParams.fromEmotion[j].Tempo))
                                ? new TempoM(translatedParams.fromColor[i].Tempo, translatedParams.fromColor[i--].overallWeight, true)
                                : new TempoM(translatedParams.fromEmotion[j].Tempo, translatedParams.fromEmotion[j--].overallWeight, false);

            TempoHigh = TempoLow = tm[0].tempo;
            //Modifier:0.8192522143648953
            //Modifier:0.7502972143648953
            //Loop through all tempos,
            for (TempoM t : tm) {
                //System.out.println(t.tempo +   "  " + t.weight + " " + t.color);
                double temp;
                if (t.tempo < 1) { //if < 1, get difference from 1 and negify it
                    temp = -1*(2 - t.tempo);
                   // if (TempoLow > t.tempo) {
                   //     TempoLow = t.tempo;
                   // }
                } else {            //if > just use it
                    temp = t.tempo;
                    //if (TempoHigh < t.tempo) {
                    //    TempoHigh = t.tempo;
                   // }

                }
                //Cumulative weight to modify tempo.
                tempSum += temp * t.weight;
            }

            //System.out.println("PreModifier:" + tempSum);
            tempSum = tempSum * 100;
            tempSum = (100 + tempSum) / 100;
            //System.out.println("FinalModtoTempo:" + tempSum);
            //System.out.println("High:" + TempoHigh);
            //System.out.println("Low:" + TempoLow);


        calcedTempo *= tempSum;
        //System.out.println("FINALTEMPO:" + calcedTempo);

        String k1;
        String k2;
        boolean relativeMinor;

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

        Tempo t1;
        Tempo t2;
        if(tempSum < 1) {
            t1 = Tempo.Moderato.getTempo((int) calcedTempo);
            t2 = Tempo.Moderato.getTempo((int) (calcedTempo + 20));
        }
        else {
            t1 = Tempo.Moderato.getTempo((int) (calcedTempo - 20));
            t2 = Tempo.Moderato.getTempo((int) calcedTempo);
        }


        MusicParams mp = new MusicParams(t1, t2, k1, k2, relativeMinor, AT1, AW1, AT2, AW2, chaos, voices);

        return mp;
    }

}