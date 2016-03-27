package ImageAPI.Objects;



/**
 * Created by Jacob on 3/26/2016.
 */
public class EmotionWeights {

    public double[] weights;

    public EmotionWeights()
    {
        this.weights = new double[]{1,1,1,1,1,1,1,1};
    }

    public EmotionWeights(double[] weights)
    {
        this.weights = weights;
    }



}
