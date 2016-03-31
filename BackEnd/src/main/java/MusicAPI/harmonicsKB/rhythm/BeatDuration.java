package MusicAPI.harmonicsKB.rhythm;

/**
 * Created by ben on 3/4/2016.
 */
public enum BeatDuration {
    DottedWhole,
    Whole,
    DottedHalf,
    Half,
    DottedQuarter,
    Quarter,
    DottedEighth,
    Eighth,
    DottedSixteenth,
    Sixteenth,
    DottedThirtySecond,
    ThirtySecond,
    DottedSixtyFourth,
    SixtyFourth;

    private int ppqn;

    BeatDuration() {
        ppqn = 96;
    }

    int getPulses() {
        int pulses = 0;
        switch(this) {
            case Whole:
                pulses = ppqn * 4;
                break;
            case Half:
                pulses = ppqn * 2;
                break;
            case Quarter:
                pulses = ppqn;
                break;
            case Eighth:
                pulses = ppqn / 2;
                break;
            case Sixteenth:
                pulses = ppqn / 4;
                break;
            case ThirtySecond:
                pulses = ppqn / 8;
                break;
            case SixtyFourth: //because why the fuck not
                pulses = ppqn / 16;
                break;
        }

        return pulses;
    }

    public int getNumberOfSixtyFourthNotes(){
        switch(this) {
            case DottedWhole: return 96;
            case Whole:
                return 64;
            case DottedHalf: return 48;
            case Half:
                return 32;
            case DottedQuarter: return 24;
            case Quarter:
                return 16;
            case DottedEighth: return 12;
            case Eighth:
                return 8;
            case DottedSixteenth: return 6;
            case Sixteenth:
                return 4;
            case DottedThirtySecond: return 3;
            case ThirtySecond:
                return 2;
            case DottedSixtyFourth: return 1;  
            case SixtyFourth: //because why the fuck not
                return 1;
        }
        return 0;
    }
}
