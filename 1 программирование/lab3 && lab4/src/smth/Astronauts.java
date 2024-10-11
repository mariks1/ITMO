package smth;


import smth.AbstractClasses.Thing;
import smth.Interfaces.Moveable;

public class Astronauts extends Thing implements Moveable {
    private int amount;

    public Astronauts(final String name, Location loc, int cnt) {
        super(name, loc);
        this.amount = cnt;
    }

    public int getAmount() {
        return this.amount;
    }




    public void move(Location previous_loc, Location new_loc) {
        previous_loc.removeThing(this.getName());
        new_loc.addThing(this);
        this.setLocation(new_loc);
    }
    public void sayMyName(){
        System.out.println("Мы - " + this.getName());
    }
}

