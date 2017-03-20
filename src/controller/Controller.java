package controller;

import javafx.event.Event;
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

    public EventHandler getConfirmButtonHandler(Spinner<Integer> s) {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                m.setTestNum(s.getValue());
                m.createStartTarget();
            }
        };
    }
    public EventHandler<? super MouseEvent> getStartTargetHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                m.setTestStarted();
                m.createTarget();
            }
        };
    }
    public EventHandler<? super MouseEvent> getTargetClickedHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                success = true;
            }
        };
    }


    public EventHandler<? super MouseEvent> getTargetHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                recordAttempt();
            }
        };
    }
    public void recordAttempt(){
        if(!success){
            m.createTarget();
            return;
        }
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
        TestResult newResult = new TestResult(startLocationX, startLocationY, locationX, locationY, time, size);
        m.recordTargetData(newResult);
        m.incrementTargetCount();
        if(m.getCurrentTargetNum() >= m.getTestNum()){
            System.out.println("finished");
            endTest();
        }else{
            success = false;
            m.createTarget();
        }
    }
    public void endTest(){
        m.setTestFinished();
    }
}
