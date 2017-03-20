package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import model.Model;
import model.Target;
import model.TestResult;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Aaron on 3/17/2017.
 */
public class TargetView implements Observer {
    Stage rootStage;
    Controller c;
    Model m;
    Pane root;
    private final String startMessage = "Set the number of attempts";

    public TargetView(Model m, Controller c, Stage s){
        rootStage = s;
        this.m = m;
        this.c = c;
        openSetNumScene();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.toString().equals("StartTarget")){
            openTestScene();
        }
        if(arg.toString().equals("TestStarted")){
            clearScene();
        }
        if(arg.toString().equals("NewTarget")){
            generateTarget(m.getCurrentTarget());
        }
        if(arg.toString().equals("TestFinished")){
            openAnalysis();
        }
    }
    public void openSetNumScene(){
        VBox root = new VBox(10);
        Scene scene = new Scene(root,300,200);
        Insets padding = new Insets(10,10,10,10);
        root.setPadding(padding);

        Text numberPrompt = new Text(startMessage);
        root.getChildren().add(numberPrompt);

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

        Button confirmNumber = new Button("START");
        confirmNumber.setOnAction(c.getConfirmButtonHandler(clickNum));
        root.getChildren().add(confirmNumber);

        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    public void openTestScene(){
        Target targetInfo = m.getCurrentTarget();
        int radius = targetInfo.getRadius();
        int x = targetInfo.getX();
        int y = targetInfo.getY();
        Circle target = new Circle();
        target.setStroke(Color.RED);
        target.setStrokeWidth(5);
        target.setStrokeType(StrokeType.INSIDE);
        target.setRadius(radius);
        target.setFill(Color.WHITE);
        target.setLayoutX(x);
        target.setLayoutY(y);
        target.setOnMouseClicked(c.getStartTargetHandler());
        Text text = new Text("START");
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(target, text);
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(stackPane,1000,1000);
        stackPane.getChildren().add(stack);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    public void clearScene(){
        root = new Pane();
        Scene scene = new Scene(root,1000,1000);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    public void generateTarget(Target targetInfo){
        root.getChildren().clear();
        //Add a randomized circle to the pane
        int radius = targetInfo.getRadius();
        int x = targetInfo.getX();
        int y = targetInfo.getY();
        Circle target = new Circle();
        target.setRadius(radius);
        target.setFill(Color.RED);
        target.setLayoutX(x);
        target.setLayoutY(y);
        target.setOnMouseClicked(c.getTargetClickedHandler());
        root.setOnMouseClicked(c.getTargetHandler());
        root.getChildren().add(target);
    }
    public void openAnalysis(){
        StackPane stack = new StackPane();
        stack.setPadding(new Insets(10,10,10,10));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Difficulty");
        yAxis.setLabel("Time (seconds)");
        final ScatterChart<Number,Number> chart = new ScatterChart<Number,Number>(xAxis,yAxis);
        chart.setLegendVisible(false);
        XYChart.Series series = new XYChart.Series();
        ArrayList<TestResult> resultList = m.getResultList();
        for(TestResult result: resultList){
            if(result.getSuccess()) {
                series.getData().add(new XYChart.Data(result.getDifficulty(), result.getTime()/1000));
            }
        }
        chart.getData().add(series);
        stack.setAlignment(Pos.CENTER);
        stack.getChildren().add(chart);
        Scene scene = new Scene(stack,1000,1000);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();

    }
}
