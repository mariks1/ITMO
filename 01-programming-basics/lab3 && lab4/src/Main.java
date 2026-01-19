
import smth.*;
import smth.AbstractClasses.Thing;
import smth.Exceptions.SpaseShipException;


public class Main {
    public static void main(String[] args) throws SpaseShipException {
        Location OuterSpace = new Location("Открытый космос");
        Location.Planet Earth = new Location.Planet("Земля", 9.8, 6431);
        Location.Planet Moon = new Location.Planet("Луна", 1.62, 1737);
        Location InsideRocket = new Location("Внутри ракеты");
        Astronomers Dabilonic = new Astronomers("Астрономы", Earth, 14);
        SpaceShip SpaceObject = new SpaceShip("Космический корабль",OuterSpace, 5,20);
        Spruts misterSpruts = new Spruts("Господин Спрутс", Earth);
        Rjigl commissionerRjigl = new Rjigl("Главный полицейский комиссар Ржигль", Moon);
        String[] requests = new String[] {"о времени ожидаемого прибытия космического корабля на Луну", "о месте предполагаемой высадки космонавтов", "об их примерном количестве"};
        Astronauts astronauts = new Astronauts("Космонавты", InsideRocket, 10);


        int cnt = requests.length;
        Telescope graviton = new Telescope(OuterSpace);
        SpaceObject.loadPassengers(astronauts.getAmount());
        Designers des = new Designers();
        Thing.InformationContainer information = commissionerRjigl.getInformationContainer();



        System.out.println("Имя: " + information.getName() + ", Местонахождение: " + information.getLocation());
        graviton.describe();
        graviton.point(Moon);
        Moon.getAllThing();
        SpaceObject.setSpeed(12);

        try {
            SpaceObject.unloadPassengers(12);
        } catch (SpaseShipException e) {
            System.out.println(e.getMessage());
        }
        Dabilonic.observe(SpaceObject, Earth);
        Dabilonic.calculate(SpaceObject);
        Dabilonic.sayPhrase(misterSpruts, "");
        misterSpruts.giveOrder("продолжать наблюдения");
        misterSpruts.call(commissionerRjigl);
        misterSpruts.sayPhrase(commissionerRjigl, "что  ожидается прибытие космического корабля с коротышками на борту, с которыми необходимо как можно скорей разделаться");
        commissionerRjigl.sayPhrase(misterSpruts, "что все необходимые меры будут приняты");
        commissionerRjigl.askForInformation(requests,cnt);;
        SpaceObject.move(OuterSpace, Moon);
        astronauts.move(InsideRocket, Moon);
        des.fuchsiaPrepare();
        des.seledochkaPrepare();
    }
}



