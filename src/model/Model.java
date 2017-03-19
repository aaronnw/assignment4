package model;

import java.util.Observable;

/**
 * Created by Aaron on 3/17/2017.
 */
public class Model extends Observable {
    private int testNum;
    public void setTestNum(Integer num) {
        testNum = num;
        System.out.println(testNum);
        setChanged();
        notifyObservers("NumSet");
    }
}
