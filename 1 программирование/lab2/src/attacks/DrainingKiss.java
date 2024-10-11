package attacks;

import ru.ifmo.se.pokemon.*;

public class DrainingKiss extends SpecialMove {
    public DrainingKiss(){
        super(Type.FAIRY,50,1);
    }

    protected String describe(){
        return "целует цель и крадёт её энергию";
    }

}
