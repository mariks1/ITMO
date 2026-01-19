package attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class SeedBomb extends PhysicalMove {
    public SeedBomb(){
        super(Type.GRASS,80,1);
    }
    protected String describe(){
        return "осыпает цель градом взрывных семян";
    }
}
