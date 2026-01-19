package smth;

import smth.Exceptions.ArrayListException;
import smth.Interfaces.LocationInterface;
import smth.AbstractClasses.*;
import java.util.ArrayList;

public class Location implements LocationInterface {
    final String name;

    public String getName() {
        return this.name;
    }
    public static class Planet extends Location {
        private final double accGravity;
        private final int radius;
        public Planet(final String name, double accGravity, int R) {
            super(name);
            this.accGravity = accGravity;
            this.radius = R;
        }
        public double getAccGravity() {
            return this.accGravity;
        }
        public int getRadius() {
            return this.radius;
        }
    }



    public ArrayList<String> listThing = new ArrayList<>();
    
    public ArrayList<String> getAllThing() {
        if (this.listThing.size() == 0) {
            throw new ArrayListException(this.name + " пустое");
        }
        return this.listThing;
    }


    public void addThing(final Thing o1) {
        this.listThing.add(o1.getName());
    }
    public void removeThing(final String o1) {
        this.listThing.remove(listThing.indexOf(o1));
    }
    public Location(final String name) {
        this.name = name;
    }
}
