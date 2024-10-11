package pokemons;

import attacks.*;
import ru.ifmo.se.pokemon.*;


public class Pansage extends Pokemon{
    public Pansage(String name,int lvl){
        super(name,lvl);
        this.setStats(50,53,48,53,48,64);
        this.setType(Type.GRASS);
        this.setMove(new FurySwipes(),new SeedBomb(),new Swagger());
    }
}
