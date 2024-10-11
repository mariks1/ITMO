package attacks;

import ru.ifmo.se.pokemon.*;

public class DazzlingGleam extends SpecialMove{
    public DazzlingGleam(){
        super(Type.FAIRY,80,1);
    }
    protected String describe(){
        return "наносит урон цели мощной вспышкой света";
    }
}
