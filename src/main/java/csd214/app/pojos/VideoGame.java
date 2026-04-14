package csd214.app.pojos;

import java.util.Objects;
import java.util.Scanner;

public abstract class VideoGame extends Product{
    String name;
    private double price;
    private String Platform;

    public String getPlatform(){return Platform;}

    public void setPlatform(String platform){this.Platform = platform;}

    public VideoGame(String name, String platform, double price, int copies){
        this.name = name;
        this.Platform = platform;
        this.price = price;
    }

    public String getName(){return name;}
    public double getPrice(){return price;}

    @Override
    public String toString(){
        return "Video Game{" +
                "platform'" + Platform +'\''+
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoGame game)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(name, game.name)
                && Objects.equals(Platform, game.Platform)
                && Double.compare(price, game.price) == 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, Platform, price);
    }


    @Override
    public void initialize(Scanner input){
        super.initialize(input);

       // Scanner platform = new Scanner(System.in);
        System.out.println("Enter the Platform: ");
        Platform = input.nextLine();
    }

    @Override
    public void edit(Scanner input){
        System.out.println("Edit " + this.Platform + ": ");
        this.Platform = getInput(input, this.Platform);
    }
}
