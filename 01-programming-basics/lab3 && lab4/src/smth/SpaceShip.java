package smth;
import smth.AbstractClasses.Thing;
import smth.Exceptions.SpaseShipException;
import smth.Interfaces.Moveable;

public class SpaceShip extends Thing implements Moveable {
    private int speed;
    private int maxCapacity;
    private int PassengerNumber;

    public SpaceShip(String name, final Location loc, int speed, int maxCapacity) {
        super(name, loc);
        this.speed = speed;
        this.maxCapacity = maxCapacity;
        this.PassengerNumber = 0;
    }

    public void loadPassengers(int PassengerNumber) throws SpaseShipException {
        if (this.getPassengerNumber() + PassengerNumber < this.getMaxCapacity()) {
            this.PassengerNumber = this.getPassengerNumber() + PassengerNumber;
        } else {
            throw new SpaseShipException("Космический корабль переполнен!");
        }
    }
    public void unloadPassengers(int ExitingPassengerser) throws SpaseShipException {
        if (this.getPassengerNumber() - ExitingPassengerser >= 0) {
            this.PassengerNumber = this.getPassengerNumber() - ExitingPassengerser;
        } else {
            throw new SpaseShipException("На космическом корабле нет такого количества пассажиров!");
        }
    }


    public void move(Location previous_loc, Location new_loc) {
        previous_loc.removeThing(this.getName());
        new_loc.addThing(this);
        this.setLocation(new_loc);
    }
    public void sayMyName(){
        System.out.println("Название корабля - " + this.getName());
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        System.out.println(this.getName() + " набрал скорость " + this.speed + " км/c");
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    public int getPassengerNumber() {
        return this.PassengerNumber;
    }


}
