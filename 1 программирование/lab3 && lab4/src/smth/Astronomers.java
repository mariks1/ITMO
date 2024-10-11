package smth;

import smth.AbstractClasses.Thing;
import smth.Interfaces.PersonInterface;
import java.math.*;

import static smth.Ranks.DAVILONIAN;

public class Astronomers extends Thing implements PersonInterface{
    private int amount;
    private Ranks ranks;
    public Astronomers(final String name,final Location loc, int cnt) {
        super(name,loc);
        this.amount = cnt;
        this.ranks = DAVILONIAN;
    }
    public int getAmount() {
        return this.amount;  
    }
    public void observe(SpaceShip a, Location.Planet t){
        System.out.println(getAmount() + " астрономов наблюдают за " + a.getName());
        if (a.getSpeed() > Math.sqrt(2 * t.getRadius() * 1000 * t.getAccGravity())) {
            System.out.println(this.getName() + " убедились, что " + a.getName() + " приобрел скорость, чтобы выйти из сферы земного притяжения");
        }
    }
    public void calculate(SpaceShip a) {
        System.out.println(this.getName() + " рассчитали, что " + a.getName() + " направляется к Луне");
    }

    public void sayPhrase(Thing t, String a) {
        System.out.println(this.getName() + " сообщили " + t.getName() + " об этом");
    }
    public void sayMyName(){
        System.out.println("Мы - " + this.getName());
    }
}
