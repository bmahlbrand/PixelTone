package MusicAPI.harmonicsKB;

public class Tempo {
	private int bpm = 0;

	public Tempo() {
		this.bpm = 120;
	}

	public Tempo(int bpm) {
		this.bpm = bpm;
	}

 	void setBPM(int bpm) {
		this.bpm = bpm;
	}

	int getBPM() {
		return this.bpm;
	}

	int getSixteenthNoteTicks() {
		return 15 / this.bpm;
	}

	int getEighthNoteTicks() {
		return 30 / this.bpm;
	}

	int getQuarterNoteTicks() {
		return 60 / this.bpm;
	}

	int getHalfNoteTicks() {
		return 120 / this.bpm;
	}

	int getWholeNoteTicks() {
		return 240 / this.bpm;
	}

	int getTripletSixteenthNoteTicks() {
		return 10 / this.bpm;
	}
	
	int getTripletEighthNoteTicks() {
		return 20 / this.bpm;
	}

	int getTripletQuarterNoteTicks() {
		return 40 / this.bpm;
	}

	int getSixteenthNoteHertz() {
		return this.bpm / 15;
	}

	int getEighthNoteHertz() {
		return this.bpm / 30;
	}

	int getQuarterNoteHertz() {
		return this.bpm / 60;
	}

	int getHalfNoteHertz() {
		return this.bpm / 120;
	}

	int getWholeNoteHertz() {
		return this.bpm / 240;
	}

	int getTripletSixteenthNoteHertz() {
		return this.bpm / 10;
	}

	int getTripletEighthNoteHertz() {
		return this.bpm / 20;
	}

	int getTripletQuarterNoteHertz(){
		return this.bpm / 40;
	}
}