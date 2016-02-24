package MusicAPI.structure;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Dictionary;

public class MetaData implements Serializable {
    String author;
    Date created_at;
    Dictionary<String, String> params;

    int divisions;

    public MetaData(File file) {

    }

    public int getDivisions() {
        return 0;
    }

    public boolean isMajor() { return true; }
}