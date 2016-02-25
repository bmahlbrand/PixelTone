package MusicAPI.virtuouso.weights;

/**
 * Created by ben on 2/22/2016.
 */
public enum WeightHeuristics {
    //    https://en.wikipedia.org/wiki/Nonchord_tone
    Chord,
    StrongBeat,
    NeighborTone,
    PassingTone,
    Root,
    Inversion;

    //this should be our generic hook for different weight schemes, different weighting for choosing likely chord progression than for picking phrase notes
    public static int heuristicCompare(WeightHeuristics type) { //needs more parameters of course, just a sketch of using different comparisons for choosing notes
        switch (type) {
            case Chord:
                break;
            case StrongBeat:
                break;
            case NeighborTone:
                break;
            case PassingTone:
                break;
            case Root:
                break;
            case Inversion:
                break;
        }

        return -1;
    }
}
