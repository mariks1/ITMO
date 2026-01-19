package attacks;

import ru.ifmo.se.pokemon.*;

public class ChargeBeam extends SpecialMove {
    public ChargeBeam(){
        super(Type.ELECTRIC,50,0.9);
    }

    protected String describe() {
        return "выстреливает концентрированным сгустком электричества";
    }
    protected void applySelfEffects(Pokemon p) {
        if (Math.random()<0.7) {
            p.setMod(Stat.SPECIAL_ATTACK,+1);
        }
    }
}
