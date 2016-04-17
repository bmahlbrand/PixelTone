package MusicAPI.harmonicsKB.rhythm;

/**
 * Created by ben on 3/5/2016.
 */
public enum Tempo {
    Larghissimo(24),
    Grave(35),
    Largo(50),
    Lento(60),
    Adagio(70),
    Andante(85),
    AndanteModerato(95),
    Moderato(110),
    AllegroModerato(120),
    Allegro(140),
    Vivace(170),
    Presto(200),
    Prestissimo(215);

    private int bpm;

    Tempo(int bpm) {
        this.bpm = bpm;
    }

    public int getBpm() {
        return bpm;
    }

    public Tempo getTempo(int bpm) {
        if(bpm < 35)
            return Larghissimo;

        if(bpm < 50)
            return Grave;

        if(bpm < 60)
            return Largo;

        if( bpm < 70)
            return Lento;

        if(bpm < 85)
            return Adagio;

        if(bpm < 95)
            return Andante;

        if(bpm < 110)
            return AndanteModerato;

        if(bpm < 120)
            return Moderato;

        if(bpm < 140)
            return AllegroModerato;

        if(bpm < 170)
            return Allegro;

        if(bpm < 200)
            return Vivace;

        if(bpm < 215)
            return Presto;

        return Prestissimo;
    }






























}
