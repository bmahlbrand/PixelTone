package MusicAPI.virtuouso;

import MusicAPI.structure.*;

import javax.sound.midi.*;
import java.io.*;

import com.google.gson.Gson;

public class MIDIGenerator {

    public static String generateMidi(String path, Composition generatedSong) {
        Sequence midiSequence;
        try {
            midiSequence = new Sequence(Sequence.PPQ, 96);

            int voiceNumber = 0;

            setTempo(midiSequence, generatedSong.getTempo());

            for (Voice instrument : generatedSong.getVoices()) {

                addTrackToMidi(instrument, midiSequence);
                voiceNumber++;

            }
            writeNotesToFile(generatedSong, path);
            writeMidiToFile(midiSequence, path);
            return path;
        } catch (Exception e) {
            midiSequence = null;
        }
        return null;
    }

    private static void writeNotesToFile(Composition notes, String filename) {
        filename = filename + ".NTS";
        System.out.println(filename);
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        File outputFile = new File(filename);
        try {
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(json.getBytes());
            outputStream.close();
        } catch (Exception e) {
            System.out.println("failure to write notes");
        }


    }

    public static String generateMidi(String path, Composition generatedSong1, Composition generatedSong2) {
        Sequence midiSequence;
        try {
            midiSequence = new Sequence(Sequence.PPQ, 96);

            int voiceNumber = 0;

            setTempo(midiSequence, generatedSong1.getTempo());

            for (Voice instrument : generatedSong1.getVoices()) {

                addTrackToMidi(instrument, midiSequence);
                voiceNumber++;

            }

            voiceNumber = 0;

            setTempo(midiSequence, generatedSong2.getTempo());

            for (Voice instrument : generatedSong2.getVoices()) {

                addTrackToMidi(instrument, midiSequence);
                voiceNumber++;

            }

            writeMidiToFile(midiSequence, path);
            return path;

        } catch (Exception e) {
        }
        return null;
    }

    private static void writeMidiToFile(Sequence midiTrack, String fileName) {
        File outputFile = new File(fileName);
        try {
            MidiSystem.write(midiTrack, 1, outputFile);
        } catch (Exception e) {
        }

    }


    private static void addTrackToMidi(Voice instrument, Sequence midiSequence) {
        Track currentTrack = midiSequence.createTrack();

        try{
            ShortMessage sustain = new ShortMessage();
            sustain.setMessage(ShortMessage.CONTROL_CHANGE, 0, 64, 127);
            MidiEvent sustainEvent = new MidiEvent(sustain, 0);
            currentTrack.add(sustainEvent);
        } catch(Exception e){}

        int currentPosition = 0;
        for (Section phrase : instrument.getSections()) {
            for (Measure currentMeasure : phrase.getMeasures()) {
                for (Beat currentBeat : currentMeasure.getBeats()) {
                    for (VoiceElement currentNote : currentBeat.getVoiceElements()) {
                        currentPosition = currentNote.addToMidiTrack(currentTrack, currentPosition);
                    }
                }
                try{
                ShortMessage sustain = new ShortMessage();
                sustain.setMessage(ShortMessage.CONTROL_CHANGE, 0, 64, 0);
                MidiEvent sustainEvent = new MidiEvent(sustain, currentPosition);
                currentTrack.add(sustainEvent);

                sustain = new ShortMessage();
                sustain.setMessage(ShortMessage.CONTROL_CHANGE, 0, 64, 127);
                sustainEvent = new MidiEvent(sustain, currentPosition);
                currentTrack.add(sustainEvent);
                } catch (Exception e){}
            }         
        }
    }

    public static int getNoteFrequency(int noteVal, int midiOctave) {
        return noteVal + midiOctave + 24;
    }

    public static void setTempo(Sequence midiSequence, int tempo) {
        Track metaTrack = midiSequence.createTrack();

        int mpq = (int) (60000000 / tempo);
        MetaMessage tempoMsg = new MetaMessage();
        try {
            tempoMsg.setMessage(0x51, new byte[]{
                    (byte) (mpq >> 16 & 0xff),
                    (byte) (mpq >> 8 & 0xff),
                    (byte) (mpq & 0xff)
            }, 3);
            MidiEvent tempoEvent = new MidiEvent(tempoMsg, 0);
            metaTrack.add(tempoEvent);
        } catch (Exception e) {
        }
    }
}