package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import model.Model;

public class Controller {

    private final Model m;

    public Controller(Model m){
        this.m = m;
    }

    public EventHandler getConfirmButtonHandler(Spinner<Integer> s) {
        return new EventHandler() {
            @Override
            public void handle(Event event) {
                m.setTestNum(s.getValue());
            }
        };
    }
}
