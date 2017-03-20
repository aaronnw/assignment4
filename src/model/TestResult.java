package model;

import javafx.geometry.Point2D;

/**
 * Created by Aaron on 3/20/2017.
 */
public class TestResult {
    double startLocationX;
    double startLocationY;
    double locationX;
    double locationY;
    long time;
    double size;
    double difficulty;
    boolean success;

    public TestResult(double startLocationX, double startLocationY, double locationX, double locationY, long time, double size, boolean success){
        this.startLocationX = startLocationX;
        this.startLocationY = startLocationY;
        this.locationX = locationX;
        this.locationY = locationY;
        this.time = time;
        this.size = size;
        this.success = success;
        calcDifficulty();
        System.out.println(startLocationX + " " + startLocationY + " "  + locationX+ " " + locationY+ " " + time+ " " + size);
    }
    //Calculate difficulty index by the log of the distance divided by the width plus 1
    public void calcDifficulty(){
        Point2D startPoint = new Point2D(startLocationX, startLocationY);
        Point2D endPoint = new Point2D(locationX, locationY);
        double distance = endPoint.distance(startPoint);
        difficulty = Math.log(distance/size + 1);
    }
    public double getStartLocationX() {
        return startLocationX;
    }

    public double getStartLocationY() {
        return startLocationY;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public long getTime() {
        return time;
    }

    public double getSize() {
        return size;
    }

    public double getDifficulty() {
        return difficulty;
    }
    public boolean getSuccess(){
        return success;
    }
}
