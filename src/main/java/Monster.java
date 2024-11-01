import java.util.Random;

public class Monster implements Square {
    private final String[] greetings = {
            "WooHoo! It's mr schleb here... Get eaten nerd!",
            "Roarrrrr! Nom nom nom! (buuuurp)",
            "UwU :3",
            "I'm gonna peck out your eyeballs!",
            "Stupid idiot you've been caught!",
            "Hahahah you fool you fell for my cunning trap!"
    };

    public String getGreeting() {
        return greeting;
    }

    private final String greeting;

    public Monster() {
        this.greeting = greetings[new Random().nextInt(greetings.length)];
    }

    @Override
    public String toString() {
        return "⬜";
    }
}
