package attacks;

import ru.ifmo.se.pokemon.*;

public class FocusBlast extends SpecialMove {
    public FocusBlast(){
        super(Type.FIGHTING,120,0.7);
    }
    protected String describe(){
        return "фокусирует боевую энергию, после чего запускает в цель сформированный шар";
    }
    protected void addOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            p.setMod(Stat.SPECIAL_DEFENSE,-1);
        }
    }
}
