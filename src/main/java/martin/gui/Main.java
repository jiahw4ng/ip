package martin.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * A JavaFX application that displays a Hello World message.
 */
public class Main extends Application {

    /**
     * Creates and displays the application's primary window.
     *
     * @param stage The primary stage provided by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);
        stage.setScene(scene);
        stage.show();
    }
}
