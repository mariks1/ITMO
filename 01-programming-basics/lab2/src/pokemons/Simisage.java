package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Simisage extends Pansage{
    public Simisage(String name,int lvl){
        super(name,lvl);
        this.setStats(75,98,63,98,63,101);
        this.setType(Type.GRASS);
        this.setMove(new FurySwipes(),new Swagger(),new SeedBomb(),new FocusBlast());
    }
}
