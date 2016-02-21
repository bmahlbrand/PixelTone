package MusicAPI.harmonicsKB.scale;

import MusicAPI.harmonicsKB.Degree;
import MusicAPI.harmonicsKB.Interval;
import MusicAPI.harmonicsKB.Mode;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;

import java.util.ArrayList;

public abstract class Scale {
	Note root;
	LimitedQueue<Note> scale;
	Mode mode;
	Interval intervals [] = null;

	Scale(Note root, Mode mode) {
		this.root = root;
		this.mode = mode;
		this.intervals = buildIntervals();
		this.scale = buildScale();
	}

	public Note getNote(Degree degree) {
		return this.scale.get(degree.toInt());
	}

	public LimitedQueue<Note> getScaleAsString() {
		if (this.scale == null) {
			this.buildScale();
		}

		return this.scale;
	}

	protected abstract LimitedQueue<Note> buildScale();
	protected abstract Interval [] buildIntervals();
}