package MusicAPI.harmonicsKB.phrasing;

/**
 * Created by ben on 2/7/2016.
 */
public enum MelodicMotion {
    Ascending, //tones climb in successions
    Descending, //opposite
    Undulating, //equal movement in both of the above directions
    Pendulum, // extreme undulation which uses a large range and large intervals
    Tile, //terrace, or cascading: a number of descending phrases in which each phrase begins on a higher pitch than the last ended
    Arc, //from a floor to a ceiling skewed towards a focus (ie could go from I to V then end on IV)
    Rise, // can refer to a plateau to contrast a section
    DoubleTonic; //smaller pendular motion in one direction
}
