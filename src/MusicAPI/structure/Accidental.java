package MusicAPI.structure;

import java.io.Serializable;

public enum Accidental implements Serializable {
    Flat(-1),
    Natural(0),
    Sharp(1);

    private int accidental;

    Accidental(int accidental) {
        this.accidental = accidental;
    }

    Accidental(char accidental) throws Exception {
        if (accidental == 'b') {
            this.accidental = -1;
        } else if (accidental == '\0') {
            this.accidental = 0;
        } else if (accidental == '#') {
            this.accidental = 1;
        } else {
            throw new Exception("Invalid accidental representation");
        }

    }
    public int toInt() {
        return this.accidental;
    }
}