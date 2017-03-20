package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by Aaron on 3/17/2017.
 */
public class TargetView implements Observer {
    Stage rootStage;
    Controller c;
    Model m;

    private final String startMessage = "Set the number of attempts";
    public TargetView(Model m, Controller c, Stage s){
        rootStage = s;
        this.m = m;
        this.c = c;
        openSetNumScene();

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().equals("NumSet")){
            openTestScene();
            generateTarget();

        }
    }
    public void openSetNumScene(){
        VBox root = new VBox();
        Scene scene = new Scene(root,300,300);
        Spinner<Integer> clickNum = new Spinner<Integer>();
        clickNum.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                int currentVal = clickNum.getValueFactory().getValue();
                if(currentVal > 1) {
                    clickNum.getValueFactory().setValue(--currentVal);
                }

            }

            @Override
            public void increment(int steps) {
                int currentVal = clickNum.getValueFactory().getValue();
                clickNum.getValueFactory().setValue(++currentVal);
            }
        });
        clickNum.getValueFactory().setValue(1);
        root.getChildren().add(clickNum);

        Text numberPrompt = new Text(startMessage);
        root.getChildren().add(numberPrompt);

        Button confirmNumber = new Button("START");
        confirmNumber.setOnAction(c.getConfirmButtonHandler(clickNum));
        root.getChildren().add(confirmNumber);

        rootStage.setScene( scene );
        rootStage.setTitle( "Fitz Law Demo" );
        rootStage.show();
    }
    public void openTestScene(){
        VBox root = new VBox();
        Scene scene = new Scene(root,1000,1000);

        rootStage.setScene( scene );
        rootStage.setTitle( "Fitz Law Demo" );
        rootStage.show();
    }
    public void generateTarget(){
        /*
        Random randomSize = new Random();
        Random randomX = new Random();
        Random randomY = new Random();
        Circle target = new Circle();
        target.setRadius(50);
        target.setFill(Color.RED);
        root.getChildren().add(target);
        */
    }
}
