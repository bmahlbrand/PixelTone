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
    ThirtySecond;

    private int ppqn;

    BeatDuration() {
        ppqn = 96;
    }

    int getPulses(Tempo tempo) {
        switch(this) {
            case Whole:
                return ppqn * tempo.getBpm() * 4;
            case Half:
                return ppqn * tempo.getBpm() * 2;
            case Quarter:
                return ppqn * tempo.getBpm();
            case Eighth:
                return (ppqn * tempo.getBpm()) / 2;
            case Sixteenth:
                return (ppqn * tempo.getBpm()) / 4;
            case ThirtySecond:
                return (ppqn * tempo.getBpm()) / 8;
        }

        return 0;
    }
}
