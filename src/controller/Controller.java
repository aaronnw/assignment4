package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.Target;
import model.TestResult;

public class Controller {

    private final Model m;
    private boolean success = false;

    public Controller(Model m){
        this.m = m;
    }

    public EventHandler<ActionEvent> getConfirmButtonHandler(Spinner<Integer> s) {
        return event -> {
            m.setTestNum(s.getValue());
            m.createStartTarget();
        };
    }
    public EventHandler<? super MouseEvent> getStartTargetHandler() {
        return (EventHandler<MouseEvent>) event -> {
            m.setTestStarted();
            m.createTarget();
        };
    }
    public EventHandler<? super MouseEvent> getTargetClickedHandler() {
        return (EventHandler<MouseEvent>) event -> success = true;
    }


    public EventHandler<? super MouseEvent> getTargetHandler() {
        return (EventHandler<MouseEvent>) event -> recordAttempt();
    }
    private void recordAttempt(){
        Target target = m.getCurrentTarget();
        //Get the location
        double locationX = target.getX();
        double locationY = target.getY();
        //Get the previous location
        double startLocationX = m.getLastTarget().getX();
        double startLocationY = m.getLastTarget().getY();
        //Get the time to reach the target
        long time = System.currentTimeMillis() - m.getTimeTargetGenerated();
        //Get the size of the target
        double size = target.getRadius();
        //Create a test result object
        TestResult newResult = new TestResult(startLocationX, startLocationY, locationX, locationY, time, size, success);
        m.recordTargetData(newResult);
        if(success) {
            m.incrementTargetCount();
        }
        if(m.getCurrentTargetNum() >= m.getTestNum()){
            System.out.println("finished");
            endTest();
        }else{
            success = false;
            m.createTarget();
        }
    }
    private void endTest(){
        m.setTestFinished();
    }

    public EventHandler<ActionEvent> getRestartHandler() {
        return event -> m.setTestRestarted();
    }
}
