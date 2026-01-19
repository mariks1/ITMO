package smth;


import smth.Interfaces.Creator;

public class Designers {

    public void fuchsiaPrepare() {
        Creator Fuchsia = new Creator() {
            public void prepare(){
                System.out.println("Фуксия надела скафандр");
            }
        };
        Fuchsia.prepare();
    }

    public void seledochkaPrepare() {
        Creator Seledochka = new Creator() {
            public void prepare(){
                System.out.println("Селедочка надела скафандр");
            }
        };
        Seledochka.prepare();
    }
}
