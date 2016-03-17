package MusicAPI.harmonicsKB.rhythm;

/**
 * Created by ben on 3/5/2016.
 */
public enum Tempo {
    Larghissimo(24),
    Grave(35),
    Largo(50),
    Lento(60),
    Larghetto(66),
    Adagio(70),
    Adagietto(75),
    Andante(85),
    Andantino(80),
    MarciaModerato(85),
    AndanteModerato(92),
    Moderato(108),
    Allegretto(112),
    AllegroModerato(116),
    Allegro(140),
    Vivace(170),
    Vivacissimo(175),
    Allegrissimo(180),
    Presto(200),
    Prestissimo(215);

    private int bpm;

    Tempo(int bpm) {
        this.bpm = bpm;
    }

    int getBpm() {
        return bpm;
    }
}
