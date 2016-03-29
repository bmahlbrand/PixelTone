package MusicAPI.harmonicsKB.scale;

import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.structure.Note;
import MusicAPI.harmonicsKB.intervals.Interval;
import MusicAPI.utils.LimitedQueue;

public class ChromaticScale extends Scale {
    public ChromaticScale(Note root, Mode mode) {
        super(root, mode);
    }

    @Override
    protected Interval[] buildIntervals() {
        Interval intervals[] = {Interval.Semitone, Interval.Semitone, Interval.Semitone, Interval.Semitone,
                Interval.Semitone, Interval.Semitone, Interval.Semitone, Interval.Semitone, Interval.Semitone,
                Interval.Semitone, Interval.Semitone, Interval.Semitone};

        return intervals;
    }

    @Override
    protected LimitedQueue<Note> buildScale() {
        LimitedQueue<Note> scale = new LimitedQueue<Note>(12);
        //int noteIndex = root.getNoteIndex();

        int noteIndex = root.getIndex();

        for (int i = 0; i < 12; i++) {
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

    public static void main(String[] args) {
        try {
            Note note = new Note("C");
            ChromaticScale cs = new ChromaticScale(note, Mode.Aeolian);
            LimitedQueue<Note> scale = cs.getScaleAsString();
            for (Note n : scale) System.out.println(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}