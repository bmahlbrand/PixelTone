package MusicAPI.structure;

import java.util.Date;
import java.util.Dictionary;

public class MetaData {
    String author;
    Date created_at;
    Dictionary<String, String> params;

    int divisions;

    public int getDivisions() {
        return 0;
    }
}