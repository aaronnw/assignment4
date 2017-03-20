package model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

/**
 * Created by Aaron on 3/17/2017.
 */
public class Model extends Observable {
    private int testNum;
    private int currentTargetNum;
    private long timeTargetGenerated;
    private Target currentTarget;
    private ArrayList<Target> targetList;
    private ArrayList<TestResult> resultList;
    private ArrayList<Double> difficultyList;

    public Model(){
        testNum = 0;
        currentTargetNum = 0;
        targetList = new ArrayList<Target>();
        resultList = new ArrayList<TestResult>();
    }
    public void setTestNum(Integer num) {
        testNum = num;
        System.out.println(testNum);
    }

    public void recordTargetData(TestResult result) {
        resultList.add(result);
    }

    public void incrementTargetCount() {
        currentTargetNum++;
        System.out.println(currentTargetNum);
    }
    public void createStartTarget(){
        currentTarget = new Target(50, 500, 500);
        targetList.add(currentTarget);
        setChanged();
        notifyObservers("StartTarget");
    }
    public void createTarget(){
        targetList.add(currentTarget);
        currentTarget = new Target();
        timeTargetGenerated = System.currentTimeMillis();
        setChanged();
        notifyObservers("NewTarget");
    }
    public Target getCurrentTarget(){
        return currentTarget;
    }
    public Target getLastTarget(){
        return targetList.get(targetList.size() -1);
    }
    public long getTimeTargetGenerated(){
        return timeTargetGenerated;
    }
    public void setTestStarted(){
        setChanged();
        notifyObservers("TestStarted");
    }
    public void setTestFinished(){
        setChanged();
        notifyObservers("TestFinished");
    }
    public int getCurrentTargetNum(){
        return currentTargetNum;
    }
    public int getTestNum(){
        return testNum;
    }

    public ArrayList<TestResult> getResultList() {
        return resultList;
    }
}
