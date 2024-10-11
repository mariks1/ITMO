package pokemons;

import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Girafarig extends Pokemon {
    public Girafarig(String name,int lvl){
        super(name,lvl);
        this.setStats(70,80,65,90,65,85);
        this.setType(Type.NORMAL,Type.PSYCHIC);
        this.setMove(new Thunder(),new Amnesia(),new Psychic(),new ChargeBeam());
    }
}
