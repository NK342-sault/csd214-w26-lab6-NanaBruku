package csd214.app.pojos;

import java.util.Scanner;

public class RPGgame extends VideoGame {
    private int mainStoryHours;

    public RPGgame(String name, String platform, double price, int copies) {
        super(name, platform, price, copies);
    }

    @Override
    public void initialize(Scanner input) {
        super.initialize(input);
        Scanner mainStory = new Scanner(System.in);
        System.out.println("How many hours is the main Story?: ");
        mainStoryHours = mainStory.nextInt();
    }

    @Override
    public void sellItem() {
        System.out.println("Selling RPG Game for " + getPlatform() + " | Story Length: " + mainStoryHours + " hours");
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
