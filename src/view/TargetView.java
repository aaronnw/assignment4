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

public class TargetView implements Observer {
    private final Stage rootStage;
    private Controller c;
    private Model m;
    private Pane root;
    private static final String START_MESSAGE = "Set the number of attempts";
    private static final String HELP_MESSAGE = "Click the circles as fast as possible" + "\n" + "to demonstrate Fitt's law";

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
        if(arg.toString().equals("TestRestarted")){
            m = new Model();
            c = new Controller(m);
            m.addObserver(this);
            clearScene();
            openSetNumScene();
        }
    }
    private void openSetNumScene(){
        VBox root = new VBox(10);
        Scene scene = new Scene(root,350,200);
        Insets padding = new Insets(10,10,10,10);
        root.setPadding(padding);

        Text numberPrompt = new Text(START_MESSAGE);
        root.getChildren().add(numberPrompt);

        Spinner<Integer> clickNum = new Spinner<>();
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
        clickNum.getValueFactory().setValue(10);
        root.getChildren().add(clickNum);

        Button confirmNumber = new Button("START");
        confirmNumber.setOnAction(c.getConfirmButtonHandler(clickNum));
        root.getChildren().add(confirmNumber);

        Text help = new Text(HELP_MESSAGE);
        root.getChildren().add(help);


        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    private void openTestScene(){
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
    private void clearScene(){
        root = new Pane();
        Scene scene = new Scene(root,1000,1000);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    private void generateTarget(Target targetInfo){
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
    private void openAnalysis(){
        VBox root = new VBox();
        root.setPadding(new Insets(10,10,10,10));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Difficulty");
        yAxis.setLabel("Time (seconds)");
        final ScatterChart<Number,Number> chart = new ScatterChart<>(xAxis,yAxis);
        chart.setLegendVisible(false);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        ArrayList<TestResult> resultList = m.getResultList();
        for(TestResult result: resultList){
            if(result.getSuccess()) {
                double xVal = result.getDifficulty();
                double yVal = result.getTime()/1000;
                XYChart.Data<Number, Number> data = new XYChart.Data<>(xVal, yVal );
                series.getData().add(data);
            }
        }
        chart.getData().add(series);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(chart);
        Scene scene = new Scene(root,1000,1000);

        Text hitPercentage = new Text();
        double p = m.calcHitPercentage();
        String percentage = (String.valueOf(Math.round(p*100)) + "% of targets were hit");
        hitPercentage.setText(percentage);
        root.getChildren().add(hitPercentage);

        Button restart = new Button("Restart");
        restart.setOnAction(c.getRestartHandler());
        root.getChildren().add(restart);


        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();

    }
}
