package MusicAPI.harmonicsKB.voicing;

import MusicAPI.utils.MersenneTwisterFast;

/**
 * Created by Ben on 4/27/2015.
 */
public enum VoiceOctaveRanges {
    Soprano(new Integer[]{new Integer(5), new Integer(6)}),
    Alto(new Integer[]{new Integer(4), new Integer(5)}),
    Tenor(new Integer[]{new Integer(2), new Integer(3)}),
    Bass(new Integer[]{new Integer(0), new Integer(1)});

    VoiceOctaveRanges(Integer[] range) {
        voiceRange = range;
    }

    private Integer[] voiceRange;

    public Integer getOctave() {
        MersenneTwisterFast rand = new MersenneTwisterFast();
        int rangeSize = voiceRange.length;
        return voiceRange[rand.nextInt(rangeSize)];
//        return voiceRange;
    }
}