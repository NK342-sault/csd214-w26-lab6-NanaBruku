package csd214.app.pojos;

import java.util.Objects;
import java.util.Scanner;

public class ActionGame extends VideoGame {
    public ActionGame(String name, String platform, double price, int copies) {
        super(name,platform,price,copies);
    }
    private boolean isMultiplayer;



    @Override
    public void initialize(Scanner input) {
        super.initialize(input);
        isMultiplayer = true;
    }

    @Override
    public void sellItem() {
        System.out.println("Selling Action Game for " + getPlatform()+ " | Multiplayer: " + isMultiplayer);
    }

    @Override
    public double getPrice() {return super.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ActionGame game)) return false;
        if (!super.equals(o)) return false;
        return isMultiplayer == game.isMultiplayer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isMultiplayer);
    }

}
