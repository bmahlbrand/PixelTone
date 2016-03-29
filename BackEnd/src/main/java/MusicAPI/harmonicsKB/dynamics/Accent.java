package MusicAPI.harmonicsKB.dynamics;

import MusicAPI.harmonicsKB.rhythm.BeatDuration;
import MusicAPI.structure.Beat;

/**
 * Created by ben on 3/5/2016.
 */
public enum Accent {
    Staccato, //last part of note should be silenced
    Staccatissimo, //shorter staccato
    Marcato, //loud as an accent, short as staccato
    Accent, //emphasized beginning and taper off quickly
    Tenuto, //multiple meanings, but lets assume "doo" in jazz articulation ie slight separation
    Unaccented;



    public int getDuration(BeatDuration duration) {
        // TODO add handling for padding total note duration and the "on" duration for accenting
        int ret = 0;

        switch(this) {
            case Staccato:
                ret = duration.getNumberOfSixtyFourthNotes() * 5;
                break;
            case Staccatissimo:
                ret = duration.getNumberOfSixtyFourthNotes() * 2;
                break;
            case Marcato:  //TODO handle loudness
                ret = duration.getNumberOfSixtyFourthNotes() * 5;
                break;
            case Accent:

                break;
            case Tenuto:

                break;
            case Unaccented:
                ret = duration.getNumberOfSixtyFourthNotes() * 6;
                break;
        }

        return ret;
    }

}
