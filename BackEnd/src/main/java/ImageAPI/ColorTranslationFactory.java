package ImageAPI;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Params.ColorParamEntry;
import java.util.List;

/**
 * Created by Jacob on 3/26/2016.
 */
public class ColorTranslationFactory {

    //https://en.wikipedia.org/wiki/Contrasting_and_categorization_of_emotions#/media/File:Plutchik-wheel.svg
    public ColorParamEntry[] translate(List<ColorEntry> ce)
    {

        //SHould already be narrowed down and filtered to useful information?? MAX of 3 colors?
        ColorParamEntry[] cpe = new ColorParamEntry[ce.size()];

        //brown, pink, red, orange, green, yellow, purple, blue, light, neutral, dark

        //Controls Tempo
        //AccentType 1 = staccato, 2 = staccatissimo, 3 = marcato, 4 = accent, 5 = tenuto, 0 = nothing
        //AccentWeight(percent)
        //Relative Major


        int i =0 ;
        for(ColorEntry c : ce) {
            //Tempo, Accent, Flat, Major
            switch (c.Color) {
                case brown:     //anticipation interest,..this will mean faces?? so maybe not count as much
                    cpe[i] = new ColorParamEntry(1.1, 1, .10, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case pink: //digust, boredom
                    cpe[i] = new ColorParamEntry( .75, 5, .75, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case red:   //rage, anger
                    cpe[i] = new ColorParamEntry(2, 4, .75, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case orange:    //surprise, anticipation
                    cpe[i] = new ColorParamEntry(1.25, 2, .5, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case green: //fear
                    cpe[i] = new ColorParamEntry(1.25, 1, .25, false);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case yellow:    //surprise, joy
                    cpe[i] = new ColorParamEntry( 1.5, 2, .75, true );
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case purple:    //disgust, loathing
                    cpe[i] = new ColorParamEntry( .75, 5, .5, false );
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case blue: //Same as sad
                    cpe[i] = new ColorParamEntry(.5, 5, .75, false );
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case light:     //weak emotion
                    cpe[i] = new ColorParamEntry(1.25, 0, 0, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case neutral:   //no effect
                    cpe[i] = new ColorParamEntry(1, 0, 0, true);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
                case dark:  //strong emotions
                    cpe[i] = new ColorParamEntry(1.5, 4, .5, false);
                    cpe[i].overallWeight = c.Percent/100;
                    break;
            }
            i++;
        }
        return cpe;
    }
}
