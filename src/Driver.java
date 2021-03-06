import javafx.application.Application;
import javafx.stage.Stage;
import view.TargetView;
import model.Model;
import controller.Controller;

/**
 * HCI assignment 4
 */
public class Driver extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model m = new Model();
        Controller c = new Controller(m);
        TargetView v = new TargetView(m ,c, primaryStage);
        m.addObserver(v);
    }
}
