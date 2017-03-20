package model;

import java.util.Random;

/**
 * Created by Aaron on 3/20/2017.
 */
public class Target {
    private int radius;
    private int x;
    private int y;

    public Target(){
        Random random = new Random();
        radius = random.nextInt(95) + 5;
        x = random.nextInt(1000-2*radius) + radius;
        y = random.nextInt(1000-2*radius) + radius;
    }
    public Target(int radius, int x, int y){
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
