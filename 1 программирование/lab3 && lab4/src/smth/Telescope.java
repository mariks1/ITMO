package smth;

public class Telescope {
    private Location direction;
    private Tube tube = new Tube(21,5);

    public Telescope(Location direction){
        this.direction = direction;
    }

    private class Tube {
        private int lenght, mirrorDiameter;
        private Tube(int lenght, int mirrorDiameter) {
            this.lenght = lenght;
            this.mirrorDiameter = mirrorDiameter;
        }
    }


    public void describe(){
        System.out.println("Гравитонный телескоп представляет собой сложное устройство, напоминающее телевизор, снабженный большой, расширяющейся к концу трубой, которая легко поворачивается и может быть направлена в любую часть лунного неба.");
    }

    public void point(Location p) {
        this.setDirection(p);
        System.out.println("Гравитонный телескоп направлен на " + p.getName());
    }

    public void setDirection(Location direction) {
        this.direction = direction;
    }


    public Location getDirection() {
        return direction;
    }
}
