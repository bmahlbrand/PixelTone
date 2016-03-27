package ImageAPI;

import ImageAPI.Objects.*;
import MusicAPI.harmonicsKB.Tempo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Jacob on 3/26/2016.
 */
public class EmotionTranslationFactory {

  //Mappings
    //From Microsoft:
        //Anger
        //Contempt
        //Disgust
        //Fear
        //Happiness
        //Neutral
        //Sadness
        //Surprise


    public FromEmotion translate(int numOfFaces, List<Face> faces)
    {
        //FromEmotion  fe = ;

        //There are no faces
        if(numOfFaces == 0)
        {
            //Tempo
            Tempo t = new Tempo(100);
            //Key
            String k = "CMajor";
            //All are 1 ??
            EmotionWeights ew = new EmotionWeights();

            return new FromEmotion(t, k, ew);
        }

        class Mapping
        {
            double weight;
            EmotionClassification ec;
        }
        //Loop through faces, tally up the emotion weights
        Mapping[] m = new Mapping[8];
        int i =0;
        //Populate mapping array
        for(EmotionClassification ec : EmotionClassification.values())
        {
            m[i].ec = ec;
            m[i++].weight = 0;
        }

        for( Face f : faces)
        {
            List<Emotion> e = f.emotions;
            for(Emotion em : e)
            {
                switch(em.emotion)
                {
                    case anger:
                        m[0].weight += em.value;
                        break;
                    case contempt:
                        m[1].weight += em.value;
                        break;
                    case disgust:
                        m[2].weight += em.value;
                        break;
                    case fear:
                        m[3].weight += em.value;
                        break;
                    case happiness:
                        m[4].weight += em.value;
                        break;
                    case neutral:
                        m[5].weight += em.value;
                        break;
                    case sadness:
                        m[6].weight += em.value;
                        break;
                    case surprise:
                        m[7].weight += em.value;
                        break;
                }
            }

        }
        //Sort mappings base on cumulative weights per emotion from all faces
        //Lambda baby - Austin Powers
        Arrays.sort(m, (a,b) -> Double.compare(a.weight, b.weight));

        //Get top 3 emotions
        //Get a percentage of that emotion
        //Get a key for that emotion
        EmotionClassification ec1 = m[0].ec;
        EmotionClassification ec2 = m[1].ec;
        EmotionClassification ec3 = m[2].ec;

        double weightTotal = m[0].weight + m[1].weight + m[2].weight;
       // double w1 = m[0].weight/weightTotal;
       // double w2 = m[1].weight/weightTotal;
       // double w3 = m[2].weight/weightTotal;

        class Entry
        {
            String key;
            double weight;
        }

        Entry[] e = new Entry[3];

        for(int q = 0; q < 3; q++)
        {
            double w1 = m[q].weight/weightTotal;

            switch (m[q].ec) {
                case anger:
                    e[q].key = getKey(EmotionClassification.anger);
                    e[q].weight = w1;
                    break;
                case contempt:
                    e[q].key = getKey(EmotionClassification.contempt);
                    e[q].weight = w1;
                    break;
                case disgust:
                    e[q].key = getKey(EmotionClassification.disgust);
                    e[q].weight = w1;
                    break;
                case fear:
                    e[q].key = getKey(EmotionClassification.fear);
                    e[q].weight = w1;
                    break;
                case happiness:
                    e[q].key = getKey(EmotionClassification.happiness);
                    e[q].weight = w1;
                    break;
                case neutral:
                    e[q].key = getKey(EmotionClassification.neutral);
                    e[q].weight = w1;
                    break;
                case sadness:
                    e[q].key = getKey(EmotionClassification.sadness);
                    e[q].weight = w1;
                    break;
                case surprise:
                    e[q].key = getKey(EmotionClassification.surprise);
                    e[q].weight = w1;
                    break;
        }


        }


    }

    private String getKey(EmotionClassification ec)
    {

        return "";
    }
}
