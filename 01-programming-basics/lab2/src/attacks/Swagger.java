package attacks;

import ru.ifmo.se.pokemon.*;

public class Swagger extends StatusMove {
    public Swagger(){
        super(Type.NORMAL,0,0.85);
    }
    protected String describe(){
        return "раздражает цель, провоцируя её на необдуманные действия";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.ATTACK,+2);
    }
    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }
}
