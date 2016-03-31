package MusicAPI.harmonicsKB.dynamics;

/**
 * Created by ben on 3/5/2016.
 */
public enum Accent {
    Staccato,
    Staccatissimo,
    Marcato,
    Accent,
    Tenuto,
    Unaccented;

    int getDuration() {
        // TODO add handling for padding total note duration and the "on" duration for accenting
        return 0;
    }

}
