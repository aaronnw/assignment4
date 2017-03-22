package model;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {
    private int testNum;
    private int currentTargetNum;
    private long timeTargetGenerated;
    private Target currentTarget;
    private final ArrayList<Target> targetList;
    private final ArrayList<TestResult> resultList;
    private double previousX;
    private double previousY;

    public Model(){
        testNum = 0;
        currentTargetNum = 0;
        targetList = new ArrayList<>();
        resultList = new ArrayList<>();
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
    public double calcHitPercentage(){
        int hit = 0;
        for(TestResult result:resultList){
            if(result.getSuccess()){
                hit ++;
            }
        }
        return (double) hit/resultList.size();
    }
    public Target getCurrentTarget(){
        return currentTarget;
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
    public void setTestRestarted(){
        setChanged();
        notifyObservers("TestRestarted");
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

    public double getPreviousX() {
        return previousX;
    }

    public void setPreviousX(double previousX) {
        this.previousX = previousX;
    }

    public double getPreviousY() {
        return previousY;
    }

    public void setPreviousY(double previousY) {
        this.previousY = previousY;
    }
}
