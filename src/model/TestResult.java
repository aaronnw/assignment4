package model;

import javafx.geometry.Point2D;

public class TestResult {
    private final double startLocationX;
    private final double startLocationY;
    private final double locationX;
    private final double locationY;
    private final double time;
    private final double size;
    private double difficulty;
    private final boolean success;

    public TestResult(double startLocationX, double startLocationY, double locationX, double locationY, double time, double size, boolean success){
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
    private void calcDifficulty(){
        Point2D startPoint = new Point2D(startLocationX, startLocationY);
        Point2D endPoint = new Point2D(locationX, locationY);
        double distance = endPoint.distance(startPoint);
        difficulty = Math.log(distance/size + 1);
    }

    public double getTime() {
        return time;
    }

    public double getDifficulty() {
        return difficulty;
    }
    public boolean getSuccess(){
        return success;
    }
}
