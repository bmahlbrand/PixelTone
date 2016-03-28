package MusicAPI.harmonicsKB.rhythm;

/**
 * Created by ben on 3/4/2016.
 */
public enum BeatDuration {
    Whole,
    Half,
    Quarter,
    Eighth,
    Sixteenth,
    ThirtySecond,
    SixtyFourth;

    private int ppqn;
    private boolean dotted;

    BeatDuration() {
        ppqn = 96;
        dotted = false;
    }

    void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    int getPulses(Tempo tempo) {
        int pulses = 0;
        switch(this) {
            case Whole:
                pulses = ppqn * tempo.getBpm() * 4;
                break;
            case Half:
                pulses = ppqn * tempo.getBpm() * 2;
                break;
            case Quarter:
                pulses = ppqn * tempo.getBpm();
                break;
            case Eighth:
                pulses = (ppqn * tempo.getBpm()) / 2;
                break;
            case Sixteenth:
                pulses = (ppqn * tempo.getBpm()) / 4;
                break;
            case ThirtySecond:
                pulses = (ppqn * tempo.getBpm()) / 8;
                break;
            case SixtyFourth: //because why the fuck not
                pulses = (ppqn * tempo.getBpm()) / 16;
                break;
        }

        if (dotted) {
            pulses += pulses / 2;
        }

        return pulses;
    }

    public int getNumberOfSixtyFourthNotes(){
        switch(this) {
            case Whole:
                return 64;
            case Half:
                return 32;
            case Quarter:
                return 16;
            case Eighth:
                return 8;
            case Sixteenth:
                return 4;
            case ThirtySecond:
                return 2;
            case SixtyFourth: //because why the fuck not
                return 1;
        }
        return 0;
    }
}
