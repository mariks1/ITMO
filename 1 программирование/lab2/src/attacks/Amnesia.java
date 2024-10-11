package attacks;

import ru.ifmo.se.pokemon.*;

public class Amnesia extends StatusMove {
    public Amnesia() {
        super(Type.PSYCHIC, 0, 1);
    }
    protected String describe(){
        return "временно очищает свой разум, чтобы не чувствовать боли";
    }
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_DEFENSE,+2);
    }
}
