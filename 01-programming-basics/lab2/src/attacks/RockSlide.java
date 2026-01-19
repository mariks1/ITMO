package attacks;

import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    public RockSlide(){
        super(Type.ROCK,75,0.9);
    }
    protected String describe(){
        return "кидает в цель огромные булыжники";
    }
    protected void addOppEffects(Pokemon p){
        Effect.flinch(p);
    }
}
