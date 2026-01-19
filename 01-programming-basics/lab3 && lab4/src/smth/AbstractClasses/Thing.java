package smth.AbstractClasses;
import smth.Location;
import java.util.Objects;
import smth.*;

public abstract class Thing {

    private String name;
    private Location location;
    public abstract void sayMyName();

    public Thing(String name, Location location) {
        this.name = name;
        this.location = location;
        location.addThing(this);
    }
    public String getName() {
        return this.name;
    }
    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

    public String toString() {return getName();}

    public interface InformationContainer{
        String getName();
        String getLocation();
    }

    public InformationContainer getInformationContainer() {
        class ThingInformationContainer implements InformationContainer {
            final String name = Thing.this.name;
            final String location = Thing.this.location.getName();

            @Override
            public String getName() {
                return this.name;
            }
            @Override
            public String getLocation() {
                return this.location;
            }
        }
        return new ThingInformationContainer();
    }

//    public int hashCode() {
//        return Objects.hash(name, getClass());
//    }
//
//    public boolean equals(final Object smb) {
//        if (this == smb)
//            return true;
//        if (smb == null || getClass() != smb.getClass())
//            return false;
//        final Thing person = (Thing) smb;
//        return Objects.equals(name, person.name);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thing thing = (Thing) o;
        return Objects.equals(name, thing.name) && Objects.equals(location, thing.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }

}