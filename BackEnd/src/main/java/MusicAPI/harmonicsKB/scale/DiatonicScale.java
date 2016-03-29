package MusicAPI.harmonicsKB.scale;

import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.intervals.Interval;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;

public class DiatonicScale extends Scale {

    public DiatonicScale(Note root, Mode mode) {
        super(root, mode);
    }

    @Override
    protected Interval[] buildIntervals() {
        return mode.getSteps();
    }

    @Override
    protected LimitedQueue<Note> buildScale() {
        scale = new LimitedQueue<>(7);
        int noteIndex = root.getIndex();

        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                scale.add(root);
                continue;
            }

            Note next = null;
            switch (intervals[i - 1]) {
                case Semitone:
                    next = scale.getLast().getHalfStep(root.getDuration());
                    break;
                case Tone:
                    next = scale.getLast().getWholeStep(root.getDuration());
                    break;
            }

            scale.add(next);
        }

        return scale;
    }

    //pick ith should be preferred as it calls these anyway
    public Note pickTonic(Note root) {
        return scale.get(Degree.Tonic.toInt());
    }

    public Note pickSuperTonic(Note root) {
        return scale.get(Degree.Supertonic.toInt());
    }

    public Note pickMediant(Note root) {
        return scale.get(Degree.Mediant.toInt());
    }

    public Note pickSubdominant(Note root) {
        return scale.get(Degree.Subdominant.toInt());
    }

    public Note pickDominant(Note root) {
        return scale.get(Degree.Dominant.toInt());
    }

    public Note pickSubmediant(Note root) {
        return scale.get(Degree.Submediant.toInt());
    }

    public Note pickLeading(Note root) {
        return scale.get(Degree.Leading.toInt());
    }

    //pick ith degree note based on root, ie root = C, index = 5, return the fifth -- G
    public Note pickIthNote(Note root, int index) {
        Note note = null;

        switch (index) {
            case 0:
                note = pickTonic(root);
                break;
            case 1:
                note = pickSuperTonic(root);
                break;
            case 2:
                note = pickMediant(root);
                break;
            case 3:
                note = pickSubdominant(root);
                break;
            case 4:
                note = pickDominant(root);
                break;
            case 5:
                note = pickSubmediant(root);
                break;
            case 6:
                note = pickLeading(root);
                break;
        }

        return note;
    }

    static boolean equal(LimitedQueue<Note> scale1, LimitedQueue<Note> scale2) {
        for (int i = 0; i < 7; i++) {
            if (scale1.get(i).equals(scale2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {

            LimitedQueue<Note> cmajor = new LimitedQueue<>(7);
            cmajor.add(new Note("C"));
            cmajor.add(new Note("D"));
            cmajor.add(new Note("E"));
            cmajor.add(new Note("F"));
            cmajor.add(new Note("G"));
            cmajor.add(new Note("A"));
            cmajor.add(new Note("B"));

            LimitedQueue<Note> eminor = new LimitedQueue<>(7);
            eminor.add(new Note("E"));
            eminor.add(new Note("F#"));
            eminor.add(new Note("G"));
            eminor.add(new Note("A"));
            eminor.add(new Note("B"));
            eminor.add(new Note("C"));
            eminor.add(new Note("D"));

            LimitedQueue<Note> dmajor = new LimitedQueue<>(7);
            dmajor.add(new Note("D"));
            dmajor.add(new Note("E"));
            dmajor.add(new Note("F#"));
            dmajor.add(new Note("G"));
            dmajor.add(new Note("A"));
            dmajor.add(new Note("B"));
            dmajor.add(new Note("C#"));

            Note note = new Note("B");
            DiatonicScale cs;// = new DiatonicScale(note, Mode.Ionian);
            LimitedQueue<Note> scale;// = cs.getScaleAsString();
            note = new Note("C");
            cs = new DiatonicScale(note, Mode.Ionian);
            scale = cs.getScaleAsString();

            if (!equal(scale, cmajor)) {
                System.out.println("c major failed");
            }

            note = new Note("E");
            cs = new DiatonicScale(note, Mode.Aeolian);
            scale = cs.getScaleAsString();

            if (!equal(scale, eminor)) {
                System.out.println("E Aeolian test failed");
            }

            note = new Note("D");
            cs = new DiatonicScale(note, Mode.Ionian);
            scale = cs.getScaleAsString();

            if (!equal(scale, dmajor)) {
                System.out.println("D Ionian test failed.");
            }

            System.out.println("E Phrygian");
            note = new Note("E");
            cs = new DiatonicScale(note, Mode.Phrygian);
            scale = cs.getScaleAsString();

            for (Note n : scale) {
                System.out.println(n.toString());
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}