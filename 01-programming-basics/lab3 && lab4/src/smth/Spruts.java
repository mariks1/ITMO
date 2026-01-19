package smth;

import smth.AbstractClasses.Thing;
import smth.Interfaces.PersonInterface;

import static smth.Ranks.MISTER;

public class Spruts extends Thing implements PersonInterface {
    private Ranks ranks;
    public Spruts(final String name, final Location loc) {
        super(name,loc);
        this.ranks = MISTER;
    }
    public void giveOrder(String a) {
        System.out.println(this.getName() + " отдал приказ " + a);
    }
    public void sayPhrase(Thing t, String a) {
        System.out.println(this.getName() + " сказал " + t.getName() + " "  + a);
    }
    public void call(Thing t){
        System.out.println(this.getName() + " позвонил " + t.getName());
    }

    public void sayMyName(){
        System.out.println("Я - " + this.getName());
    }
}