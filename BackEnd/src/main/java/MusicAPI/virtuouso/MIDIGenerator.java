package MusicAPI.virtuouso;

import MusicAPI.structure.*;
import javax.sound.midi.*;
import java.io.*;

public class MIDIGenerator{

	public static Sequence generateMidi(Composition generatedSong){
		Sequence midiSequence;
		try{
			midiSequence = new Sequence(Sequence.PPQ, 96);

			int voiceNumber = 0;

			setTempo(midiSequence, generatedSong.getTempo());

			for(Voice instrument: generatedSong.getVoices()){

				addTrackToMidi(instrument, midiSequence);
				voiceNumber++;

			}

		}
		catch (Exception e){
			midiSequence = null;
		}
		return midiSequence;
	}


	private static void writeMidiToFile(Sequence midiTrack, String fileName){
		File outputFile = new File(fileName);
		try{
			MidiSystem.write(midiTrack, 1, outputFile);
		}
		catch (Exception e){}

	}


	private static void addTrackToMidi(Voice instrument, Sequence midiSequence){
		Track currentTrack = midiSequence.createTrack();

		int currentPosition = 0;
		for(Section phrase: instrument.getSections()){
			for (Measure currentMeasure: phrase.getMeasures()){
				for (Beat currentBeat: currentMeasure.getBeats()){
					for (VoiceElement currentNote: currentBeat.getVoiceElements()){
						currentPosition = currentNote.addToMidiTrack(currentTrack, currentPosition);
					}
				}
			}
		}
	}

	public static int getNoteFrequency(int noteVal, int midiOctave){
		return noteVal+midiOctave + 24;
	}

	public static void setTempo(Sequence midiSequence, int tempo){
		Track metaTrack = midiSequence.createTrack();

		int mpq = (int)(60000000 / tempo);
		MetaMessage tempoMsg = new MetaMessage();
		try{
			tempoMsg.setMessage(0x51,new byte[] {
				(byte)(mpq>>16 & 0xff),
				(byte)(mpq>>8 & 0xff),
				(byte)(mpq & 0xff)
			},3);
			MidiEvent tempoEvent = new MidiEvent(tempoMsg,0);
			metaTrack.add(tempoEvent);
		}
		catch(Exception e){}
	}
}
