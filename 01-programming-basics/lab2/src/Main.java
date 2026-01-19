import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        b.addAlly(new Girafarig("Серега", 1));
        b.addAlly(new Gallade("Андрейчмо", 1));
        b.addAlly(new Kirlia("фен", 1));
        b.addFoe(new Pansage("меф", 1));
        b.addFoe(new Ralts("исаак ньютон", 1));
        b.addFoe(new Simisage("арбитраж криптовалютович", 1));
        b.go();
    }
}
