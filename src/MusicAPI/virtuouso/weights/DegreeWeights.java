package MusicAPI.virtuouso.weights;

/**
 * Created by ben on 2/6/2016.
 */
public class DegreeWeights {

    private static int degreeWeight[][] = {
            {3,  3,    3,    3,    3,   3,    3}, //tonic to ...
            {0,  3,    0,    0,    4,   0,    1}, //supertonic to ...
            {0,  0,    2,    4,    9,   4,    0}, //mediant to ...
            {4,  2,    0,    2,    2,   0,    1}, //etc...
            {5,  0,    0,    0,    3,   2,    0},
            {0,  5,    0,    3,    0,   2,    0},
            {9,  0,    0,    0,    0,   0,    1}
    };

    public static int getDegreeWeight(int i, int j) {
        return degreeWeight[i][j];
    }
}
