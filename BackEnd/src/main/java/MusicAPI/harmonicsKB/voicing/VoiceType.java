package MusicAPI.harmonicsKB.voicing;

/**
 * Created by ben on 4/28/2015.
 */
public enum VoiceType {
    Bass(0),
    Tenor(1),
    Alto(2),
    Soprano(3);

    int voiceType;

    VoiceType(int index) {
        voiceType = index;
    }

    public int toInt() {
        return voiceType;
    }
}
