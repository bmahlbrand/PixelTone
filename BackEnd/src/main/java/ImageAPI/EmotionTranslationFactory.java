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
  class EmotionEntry
  {
      String key1;
      String key2;
      double Tempo;
      double AccentWeight;
      double FlatOSharpWeight;

      public EmotionEntry(String k, String k2, double t, double w)
      {
          this.key1 = k;
          this.key2 = k2;
          this.Tempo = t;
          this.AccentWeight = w;
      }

      //Sprint 3,, rythm, melody?? loudness??
  }

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


            EmotionEntry e;

            double weight;
        }

        Entry[] e = new Entry[3];

        for(int q = 0; q < 3; q++)
        {
            double w1 = m[q].weight/weightTotal;

            switch (m[q].ec)
            {
                case anger:
                    e[q].e = getEmotion(EmotionClassification.anger);
                    e[q].weight = w1;
                    break;
                case contempt:
                    e[q].e = getEmotion(EmotionClassification.contempt);
                    e[q].weight = w1;
                    break;
                case disgust:
                    e[q].e = getEmotion(EmotionClassification.disgust);
                    e[q].weight = w1;
                    break;
                case fear:
                    e[q].e = getEmotion(EmotionClassification.fear);
                    e[q].weight = w1;
                    break;
                case happiness:
                    e[q].e = getEmotion(EmotionClassification.happiness);
                    e[q].weight = w1;
                    break;
                case neutral:
                    e[q].e = getEmotion(EmotionClassification.neutral);
                    e[q].weight = w1;
                    break;
                case sadness:
                    e[q].e = getEmotion(EmotionClassification.sadness);
                    e[q].weight = w1;
                    break;
                case surprise:
                    e[q].e = getEmotion(EmotionClassification.surprise);
                    e[q].weight = w1;
                    break;
            }
        }



    }

    //Ionian is major
    //aeolian is minor
    //fifth is notetype?
    private EmotionEntry getEmotion(EmotionClassification ec)
    {
        //Mapping  (Key1, Key2, TempoWeight, Acccent Weight)  (add flatOSharp weight)
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
        EmotionEntry e;

        switch (ec)
        {
            case anger: // B Major, F Major, fastest tempo weight, highest accent weight
                return new EmotionEntry("Bma", "Fma", 2, 2);

            case contempt:              //Mix of anger/disgust
                return new EmotionEntry("Bma", "Fmi", .5, 1.5);

            case disgust:               //Minor
                return new EmotionEntry("Fmin", "Emi", .75, .50);

            case fear:
                return new EmotionEntry("Ebmin", "D#min", 1.25, 1.75);

            case happiness:
                return new EmotionEntry("Gmaj", "Ema", 1.25, 1.25);

            case neutral:
                return new EmotionEntry("Cma", "Cma", 1, 1);

            case sadness:
                return new EmotionEntry("Cmin", "Bmin", .5, .25);
            
            case surprise:
                return new EmotionEntry("Ama", "Dma", 1.5, 1.5);
        }
        return new EmotionEntry("Cma", "Cma", 1, 1);
    }
}
