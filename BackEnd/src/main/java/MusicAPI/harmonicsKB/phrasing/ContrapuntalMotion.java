package MusicAPI.harmonicsKB.phrasing;

import java.util.Collection;

/**
 * Created by ben on 4/26/2015.
 */
public enum ContrapuntalMotion {
    Parallel(0), //harmonics are parallel with other voice being compared
    Similar(1), // both lines move up, or both lines move down, but the interval between them is different in the first chord and the second chord
    Contrary(2), //motion in opposite directions - when one of the lines moves up, the other line moves down (in inversion).
    Oblique(3); // motion of one melodic line while the other remains at the same pitch

    private int value;

    ContrapuntalMotion(int value) {
        this.value = value;
    }

//    public static Collection contrarize() {
//        return ;
//    }



    public int toInt() {
        return value;
    }
}
