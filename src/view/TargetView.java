package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Aaron on 3/17/2017.
 */
public class TargetView implements Observer {

    private final String startMessage = "Set the number of attempts";
    public TargetView(Model m, Controller c, Stage primaryStage){

        VBox root = new VBox();
        Scene scene = new Scene(root,500,500);
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
/*
        Circle target = new Circle();
        target.setRadius(50);
        target.setFill(Color.RED);
        root.getChildren().add(target);
*/
        Text numberPrompt = new Text(startMessage);
        root.getChildren().add(numberPrompt);

        Button confirmNumber = new Button("START");
        confirmNumber.setOnAction(c.getConfirmButtonHandler(clickNum));
        root.getChildren().add(confirmNumber);

        primaryStage.setScene( scene );
        primaryStage.setTitle( "Fitz Law Demo" );
        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().equals("NumSet")){
            openTest();
        }
    }
    public void openTest(){}
}
