package smth;

import smth.AbstractClasses.Thing;
import smth.Interfaces.PersonInterface;

import static smth.Ranks.CHIEFCOMMISSIONER;

public class Rjigl extends Thing implements PersonInterface {
    private Ranks ranks;
    public Rjigl(final String name, final Location loc) {
        super(name,loc);
        this.ranks = CHIEFCOMMISSIONER;
    }

    public void sayPhrase(Thing t, String a) {
        System.out.println(this.getName() + " сказал " + t.getName() + " "  + a);
    }
    
    public void askForInformation(String[] asking,int n) {
        String a = this.getName() + " попросил сообщить";
        for (int i = 0; i < n; i++) {
            if (i == n-1) {
                a = a + " " + asking[i] + ".";
            }
            else {
                a = a + " " + asking[i] + ",";
            }
        }
        System.out.println(a);
    }



    public void sayMyName(){
        System.out.println("Я - " + this.getName());
    }
}
