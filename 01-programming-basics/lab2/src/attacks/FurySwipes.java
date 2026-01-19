package attacks;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Type;

public class FurySwipes extends PhysicalMove {
    public FurySwipes(){
        super(Type.NORMAL,18,0.8);
    }
    protected String describe(){
        return "проводит серию яростных ударов когтями";
    }
}
