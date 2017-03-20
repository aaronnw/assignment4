package model;

import java.util.Random;

public class Target {
    private final int radius;
    private final int x;
    private final int y;

    Target(){
        Random random = new Random();
        radius = random.nextInt(95) + 5;
        x = random.nextInt(1000-2*radius) + radius;
        y = random.nextInt(1000-2*radius) + radius;
    }
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
