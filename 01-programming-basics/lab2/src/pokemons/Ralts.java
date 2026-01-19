package pokemons;

import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Ralts extends Pokemon {
    public Ralts(String name,int lvl){
        super(name,lvl);
        this.setStats(28,25,25,45,35,40);
        this.setType(Type.PSYCHIC,Type.FAIRY);
        this.setMove(new Psychic(),new DazzlingGleam());
    }
}
