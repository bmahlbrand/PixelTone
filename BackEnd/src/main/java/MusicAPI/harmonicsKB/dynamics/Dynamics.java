package MusicAPI.harmonicsKB.dynamics;

/**
 * Created by ben on 3/5/2016.
 */
public enum Dynamics {
    PianoPianissimo(8),
    Pianissimo(24),
    Piano(40),
    MezzoPiano(56),
    MezzoForte(72),
    Forte(88),
    Fortissimo(104),
    ForteFortissimo(120);

    //loudness or intensity of the hit
    int velocity;

    Dynamics(int velocity) {
        this.velocity = velocity;
    }

    public int getVolume() {
        return velocity;
    }
}
