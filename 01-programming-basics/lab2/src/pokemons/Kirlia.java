package pokemons;

import attacks.DazzlingGleam;
import attacks.Psychic;
import ru.ifmo.se.pokemon.Type;

public class Kirlia extends Ralts{
    public Kirlia(String name,int lvl) {
        super(name,lvl);
        this.setStats(38,35,35,65,55,50);
        this.setType(Type.PSYCHIC,Type.FAIRY);
        this.setMove(new Psychic(),new DazzlingGleam());
    }
}
