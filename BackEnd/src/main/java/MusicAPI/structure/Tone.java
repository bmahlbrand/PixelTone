package MusicAPI.structure;

import java.io.Serializable;

public class Tone implements Serializable {
    char tone;
    Accidental accidental;

    Tone(char tone, Accidental accidental) {
        this.tone = tone;
        this.accidental = accidental;
    }

    Tone(String tone) {
        this.tone = tone.charAt(0);

        if (tone.length() > 1) {
            if (tone.charAt(1) == '#') {
                this.accidental = Accidental.Sharp;
            } else {
                this.accidental = Accidental.Flat;
            }

        } else {
            this.accidental = Accidental.Natural;
        }
    }

    Tone halfStep() {
        return fromIndex((index() + 1) % 12);
    }

    Tone wholeStep() {
        if ((tone == 'B' && accidental == Accidental.Natural)
                || (tone == 'E' && accidental == Accidental.Natural)) {
            return fromIndex((index() + 2) % 12);
        } else {
            return fromIndex((index() + 2) % 12);
        }
    }

    static Tone fromIndex(int index) {
        char tone = '\0';
        Accidental accidental = Accidental.Natural;

        switch(index) {
            case 0:
                tone = 'C';
                accidental = Accidental.Natural;
                break;
            case 1:
                tone = 'C';
                accidental = Accidental.Sharp;
                break;
            case 2:
                tone = 'D';
                accidental = Accidental.Natural;
                break;
            case 3:
                tone = 'D';
                accidental = Accidental.Sharp;
                break;
            case 4:
                tone = 'E';
                accidental = Accidental.Natural;
                break;
            case 5:
                tone = 'F';
                accidental = Accidental.Natural;
                break;
            case 6:
                tone = 'F';
                accidental = Accidental.Sharp;
                break;
            case 7:
                tone = 'G';
                accidental = Accidental.Natural;
                break;
            case 8:
                tone = 'G';
                accidental = Accidental.Sharp;
                break;
            case 9:
                tone = 'A';
                accidental = Accidental.Natural;
                break;
            case 10:
                tone = 'A';
                accidental = Accidental.Sharp;
                break;
            case 11:
                tone = 'B';
                accidental = Accidental.Natural;
                break;
        }

        return new Tone(tone, accidental);
    }

    int index() {
        switch(tone) {
            case 'C':
                switch (accidental) {
                    case Flat:
                        return 11;
                    case Natural:
                        return 0;
                    case Sharp:
                        return 1;
                }
            case 'D':
                switch (accidental) {
                    case Flat:
                        return 1;
                    case Natural:
                        return 2;
                    case Sharp:
                        return 3;
                }
            case 'E':
                switch (accidental) {
                    case Flat:
                        return 3;
                    case Natural:
                        return 4;
                    case Sharp:
                        return 5;
                }
            case 'F':
                switch (accidental) {
                    case Flat:
                        return 4;
                    case Natural:
                        return 5;
                    case Sharp:
                        return 6;
                }
            case 'G':
                switch (accidental) {
                    case Flat:
                        return 6;
                    case Natural:
                        return 7;
                    case Sharp:
                        return 8;
                }
            case 'A':
                switch (accidental) {
                    case Flat:
                        return 8;
                    case Natural:
                        return 9;
                    case Sharp:
                        return 10;
                }
            case 'B':
                switch (accidental) {
                    case Flat:
                        return 10;
                    case Natural:
                        return 11;
                    case Sharp:
                        return 0;
                }
        }

//        TODO add exception throwing
        return -1;
    }

    @Override
    public String toString() {
        String s = new String();
        s += this.tone;
        if (this.accidental == Accidental.Sharp) {
            s += '#';
        } else if (this.accidental == Accidental.Flat) {
            s += 'b';
        }

        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!Tone.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Tone other = (Tone)obj;

        if (this.tone == other.tone
            && this.accidental == other.accidental) {
            return true;
        }

        return false;
    }
}