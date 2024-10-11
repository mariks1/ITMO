package attacks;

import ru.ifmo.se.pokemon.*;

public class Psychic extends SpecialMove {
    public Psychic(){
        super(Type.PSYCHIC,90,1);
    }
    protected String describe(){
        return "атакует цель мощным телекинезом";
    }
    protected void applyOppEffects(Pokemon p) {
        if (Math.random()<0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE,-1);
        }
    }
}
