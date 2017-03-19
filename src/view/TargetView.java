package view;

import controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Aaron on 3/17/2017.
 */
public class TargetView implements Observer {
    public TargetView(Model m, Controller c, Stage primaryStage){

        VBox root = new VBox();
        Scene scene = new Scene(root,1000,1000);
        Spinner<Integer> clickNum = new Spinner<Integer>();
        root.getChildren().add(clickNum);

        Image image = new Image(getClass().getResourceAsStream("redcircle.png"));
        Label target = new Label("Target");
        target.setGraphic(new ImageView(image));

        root.getChildren().add(target);
        primaryStage.setScene( scene );
        primaryStage.setTitle( "Fitz Law Demo" );
        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
