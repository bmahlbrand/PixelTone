package ImageAPI.Params;

import ImageAPI.Objects.ColorEntry;
import ImageAPI.Objects.Face;

import java.util.List;

/**
 * Created by Jacob on 2/22/2016.
 */
public class GenerationParams {
    public int numberOfFaces;
    public List<Face> faces;
    public List<ColorEntry> colorEntries;
    public int prefs;
    public int chaos;
    public int voices;
    public String imageKey;
    public String name;
}
