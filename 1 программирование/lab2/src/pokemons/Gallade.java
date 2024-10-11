package pokemons;

import attacks.DazzlingGleam;
import attacks.Psychic;
import attacks.RockSlide;
import ru.ifmo.se.pokemon.Type;

public class Gallade extends Kirlia{
    public Gallade(String name,int lvl) {
        super(name,lvl);
        this.setStats(68,125,65,65,115,80);
        this.setType(Type.PSYCHIC,Type.FIGHTING);
        this.setMove(new Psychic(),new DazzlingGleam(),new RockSlide());
    }
}
