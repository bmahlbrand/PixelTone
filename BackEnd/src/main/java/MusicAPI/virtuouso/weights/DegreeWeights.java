package MusicAPI.virtuouso.weights;

/**
 * Created by ben on 2/6/2016.
 */
public class DegreeWeights {

    private static Double degreeWeight[][] = {
            {3.0,  3.0,    3.0,    3.0,    3.0,   3.0,    3.0}, //tonic to ...
            {0.0,  3.0,    0.0,    0.0,    4.0,   0.0,    1.0}, //supertonic to ...
            {0.0,  0.0,    2.0,    4.0,    9.0,   4.0,    0.0}, //mediant to ...
            {4.0,  2.0,    0.0,    2.0,    2.0,   0.0,    1.0}, //etc...
            {5.0,  0.0,    0.0,    0.0,    3.0,   2.0,    0.0},
            {0.0,  5.0,    0.0,    3.0,    0.0,   2.0,    0.0},
            {9.0,  0.0,    0.0,    0.0,    0.0,   0.0,    1.0}
    };

    public static Double get(int i, int j) {
        return degreeWeight[i][j];
    }
}
