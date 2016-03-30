package ImageAPI;

import ImageAPI.Objects.*;
import ImageAPI.Params.EmotionParamEntry;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jacob on 3/26/2016.
 */
public class EmotionTranslationFactory {

    public EmotionParamEntry[] translate(int numOfFaces, List<Face> faces)
    {
        //There are no faces
        if(numOfFaces == 0)
        {
            EmotionParamEntry e = new EmotionParamEntry("Cmajor", "Cmajor", 1, 1);
            e.overallWeight = 0;
            EmotionParamEntry[] ar = new EmotionParamEntry[1];
            ar[0] = e;
            return ar;
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

        EmotionParamEntry[] e = new EmotionParamEntry[3];

        for(int q = 0; q < 3; q++)
        {
            double w1 = m[q].weight/weightTotal;

            switch (m[q].ec)
            {
                case anger:
                    e[q] = getEmotion(EmotionClassification.anger);
                    e[q].overallWeight = w1;
                    break;
                case contempt:
                    e[q] = getEmotion(EmotionClassification.contempt);
                    e[q].overallWeight = w1;
                    break;
                case disgust:
                    e[q] = getEmotion(EmotionClassification.disgust);
                    e[q].overallWeight = w1;
                    break;
                case fear:
                    e[q] = getEmotion(EmotionClassification.fear);
                    e[q].overallWeight = w1;
                    break;
                case happiness:
                    e[q] = getEmotion(EmotionClassification.happiness);
                    e[q].overallWeight = w1;
                    break;
                case neutral:
                    e[q] = getEmotion(EmotionClassification.neutral);
                    e[q].overallWeight = w1;
                    break;
                case sadness:
                    e[q] = getEmotion(EmotionClassification.sadness);
                    e[q].overallWeight = w1;
                    break;
                case surprise:
                    e[q] = getEmotion(EmotionClassification.surprise);
                    e[q].overallWeight = w1;
                    break;
            }
        }

        return e;

    }

    //Ionian is major
    //aeolian is minor
    //fifth is notetype?
    private EmotionParamEntry getEmotion(EmotionClassification ec)
    {
        //Mapping  (Key1, Key2, TempoWeight, Acccent Weight)  (add flatOSharp weight  <1 flats, >1 sharps)
            //Anger
                //Bmajor, Fmajor, 2, 2
            //Neutral
                //C Major,   1, 1
            //Happiness
                //G Major, Emajor, 1.25, 1.25
            //Surprise
                //A major, D major , 1.5, 1.5
            //Fear
                //Eb minor,  D# minor,  1.25, 1.75  (Possible?)
            //Disgust
                //F minor, Eminor, .75, .50
            //Sadness
                //C minor, Bminor, .5, .25
            //Contempt (mix of anger, disgust)
                //Bmajor, Fminor, .5, 1.5
        EmotionParamEntry e;

        switch (ec)
        {
            case anger:
                return new EmotionParamEntry("B-major", "F-major", 2, 2);

            case contempt:              //Mix of anger/disgust
                return new EmotionParamEntry("B-major", "F-minor", .5, 1.5);

            case disgust:               //Minor
                return new EmotionParamEntry("F-minor", "E-minor", .75, .50);

            case fear:
                return new EmotionParamEntry("Eb-minor", "D#-minor", 1.25, 1.75);

            case happiness:
                return new EmotionParamEntry("G-major", "E-major", 1.25, 1.25);

            case neutral:
                return new EmotionParamEntry("C-major", "C-major", 1, 1);

            case sadness:
                return new EmotionParamEntry("C-minor", "B-minor", .5, .25);

            case surprise:
                return new EmotionParamEntry("A-major", "D-major", 1.5, 1.5);
        }
        return new EmotionParamEntry("C-major", "C-major", 1, 1);
    }
}
