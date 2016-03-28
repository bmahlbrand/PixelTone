package MusicAPI.harmonicsKB.phrasing;

import MusicAPI.harmonicsKB.phrasing.frame.FrameFocus;
import MusicAPI.structure.Note;
import MusicAPI.structure.Tone;

/**
 * Created by ben on 2/7/2016.
 */
public class ModalFrame {
    Tone floor;
    Tone ceiling;
    Tone pivot;

    FrameFocus upperFocus;
    FrameFocus lowerFocus;

    MelodicMotion motion;
}
