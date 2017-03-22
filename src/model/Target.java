package model;

import java.util.Random;

public class Target {
    private final int radius;
    private final int x;
    private final int y;

    //Generate a random target object if given no arguments
    Target(){
        Random random = new Random();
        radius = random.nextInt(95) + 5;
        x = random.nextInt(1000-2*radius) + radius;
        y = random.nextInt(1000-2*radius) + radius;
    }
    //Create a target object based on the given arguments
    Target(int radius, int x, int y){
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public int getRadius() {
        return radius;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
