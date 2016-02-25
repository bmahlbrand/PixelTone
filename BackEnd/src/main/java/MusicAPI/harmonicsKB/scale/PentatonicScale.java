package MusicAPI.harmonicsKB.scale;

import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;

public class PentatonicScale extends DiatonicScale {

    public PentatonicScale(Note root, Mode mode) {
        super(root, mode);

        scale.remove(1); //remove the second and the fifth
        scale.remove(4);
    }

    public static void main(String[] args) {
        try {
            Note note = new Note("E");
            PentatonicScale cs = new PentatonicScale(note, Mode.Aeolian);
            LimitedQueue<Note> scale = cs.getScaleAsString();
            for (Note n : scale)
                System.out.println(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}