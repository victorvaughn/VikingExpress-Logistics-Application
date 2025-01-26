package errorists;

import errorists.controllers.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppController appController = new AppController(primaryStage);
        appController.showMainView();
    }
    }

