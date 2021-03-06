package ImageAPI;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Params.ColorParamEntry;
import MusicAPI.harmonicsKB.dynamics.Accent;

import java.util.List;

/**
 * Created by Jacob on 3/26/2016.
 */
public class ColorTranslationFactory {
    //https://en.wikipedia.org/wiki/Contrasting_and_categorization_of_emotions#/media/File:Plutchik-wheel.svg
    public ColorParamEntry[] translate(List<ColorEntry> ce) {


        ColorParamEntry[] cpe = new ColorParamEntry[ce.size()];

        //brown, pink, red, orange, green, yellow, purple, blue, light, neutral, dark

        //Controls Tempo
        //AccentType 1 = staccato, 2 = staccatissimo, 3 = marcato, 4 = accent, 5 = tenuto, 0 = nothing
        //AccentWeight(percent)
        //Relative Minor

        int i = 0;
        for (ColorEntry c : ce) {
            switch (c.Color) {
                case brown:     //anticipation interest,..this will mean faces?? so maybe not count as much
                    cpe[i] = new ColorParamEntry(1.1, Accent.Staccato, .10, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case pink: //disgust, boredom
                    cpe[i] = new ColorParamEntry(.75, Accent.Tenuto, .75, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case red:   //rage, anger
                    cpe[i] = new ColorParamEntry(2, Accent.Accent, .75, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case orange:    //surprise, anticipation
                    cpe[i] = new ColorParamEntry(1.25, Accent.Staccatissimo, .5, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case green: //fear
                    cpe[i] = new ColorParamEntry(1.25, Accent.Staccato, .25, true);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case yellow:    //surprise, joy
                    cpe[i] = new ColorParamEntry(1.5, Accent.Staccatissimo, .75, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case purple:    //disgust, loathing
                    cpe[i] = new ColorParamEntry(.75, Accent.Tenuto, .5, true);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case blue: //Same as sad
                    cpe[i] = new ColorParamEntry(.5, Accent.Tenuto, .75, true);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case light:     //weak emotion
                    cpe[i] = new ColorParamEntry(1.25, Accent.Unaccented, 0, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case neutral:   //no effect
                    cpe[i] = new ColorParamEntry(1, Accent.Unaccented, 0, false);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
                case dark:  //strong emotions
                    cpe[i] = new ColorParamEntry(1.5, Accent.Accent, .5, true);
                    cpe[i].overallWeight = c.Percent / 100;
                    break;
            }
            i++;
        }
        return cpe;
    }
}
