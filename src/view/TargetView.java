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
    private static final String START_MESSAGE = "Set the number of targets";
    private static final String HELP_MESSAGE = "Click the circles as fast as possible" + "\n" + "\n" + "When the number is reached a results graph will appear";
    private static final double TEST_SIZE = 1100;

    public TargetView(Model m, Controller c, Stage s){
        rootStage = s;
        this.m = m;
        this.c = c;
        openSetNumScene();
    }

    //Updates based on model changes
    @Override
    public void update(Observable o, Object arg) {
        switch(arg.toString()){
            case "StartTarget" : openTestScene();
            break;
            case "TestStarted" : clearScene();
            break;
            case "NewTarget": generateTarget(m.getCurrentTarget());
            break;
            case "TestFinished": openAnalysis();
            break;
            case "TestRestarted": restartTest();
            break;
        }
    }
    //Opens the first scene to get the number of targets to hit
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

        rootStage.setMinWidth(350);
        rootStage.setMinHeight(200);
        rootStage.setWidth(350);
        rootStage.setHeight(200);
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
        text.setOnMouseClicked(c.getStartTargetHandler());
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(target, text);
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(stackPane,TEST_SIZE,TEST_SIZE);
        stackPane.getChildren().add(stack);
        rootStage.setWidth(TEST_SIZE);
        rootStage.setHeight(TEST_SIZE);
        rootStage.setResizable(false);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();
    }
    private void clearScene(){
        root = new Pane();
        Scene scene = new Scene(root,TEST_SIZE,TEST_SIZE);
        rootStage.setWidth(TEST_SIZE);
        rootStage.setHeight(TEST_SIZE);
        rootStage.setResizable(false);
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
        XYChart.Series<Number, Number> hitSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> missSeries = new XYChart.Series<>();

        ArrayList<TestResult> resultList = m.getResultList();
        boolean showMisses = false;
        for(TestResult result: resultList){
            if(result.getSuccess()) {
                double xVal = result.getDifficulty();
                double yVal = result.getTime()/1000;
                XYChart.Data<Number, Number> hitData = new XYChart.Data<>(xVal, yVal );
                hitSeries.getData().add(hitData);
            }else{
                double xVal = result.getDifficulty();
                double yVal = result.getTime()/1000;
                XYChart.Data<Number, Number> missData = new XYChart.Data<>(xVal, yVal );
                missSeries.getData().add(missData);
                showMisses = true;
            }
        }

        hitSeries.setName("Hit targets");
        missSeries.setName("Missed targets");
        chart.getData().add(hitSeries);
        if(showMisses) {
            chart.getData().add(missSeries);
        }
        chart.setPadding(new Insets(10,10,10,10));
        chart.getStylesheets().add("/view/graphStylesheet.css");
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(chart);

        Text hitPercentage = new Text();
        double p = m.calcHitPercentage();
        String percentage = (String.valueOf(Math.round(p*100)) + "% of targets were hit");
        hitPercentage.setText(percentage);

        root.getChildren().add(hitPercentage);

        Button restart = new Button("Restart");
        restart.setOnAction(c.getRestartHandler());
        root.getChildren().add(restart);

        root.setSpacing(10);
        Scene scene = new Scene(root,TEST_SIZE,TEST_SIZE);

        rootStage.setMinHeight(450);
        rootStage.setMinWidth(450);
        rootStage.setScene( scene );
        rootStage.setTitle( "Fitt's Law Demo" );
        rootStage.centerOnScreen();
        rootStage.show();

    }
    private void restartTest(){
        m = new Model();
        c = new Controller(m);
        m.addObserver(this);
        openSetNumScene();
    }
}
