package martin.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import martin.Martin;
import martin.exception.MartinException;

/**
 * A JavaFX application that provides a graphical interface for Martin.
 */
public class Main extends Application {

    private static final String WINDOW_TITLE = "Martin";
    private static final double WINDOW_WIDTH = 600;
    private static final double WINDOW_HEIGHT = 450;

    private Martin martin;
    private TextArea transcript;
    private TextField commandInput;
    private Button sendButton;

    /**
     * Creates and displays the application's primary window.
     *
     * @param stage The primary stage provided by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        this.transcript = new TextArea();
        this.transcript.setEditable(false);
        this.transcript.setWrapText(true);

        this.commandInput = new TextField();
        this.commandInput.setPromptText("Enter a command");
        this.sendButton = new Button("Send");
        this.sendButton.setDefaultButton(true);
        this.sendButton.setOnAction(event -> this.submitCommand());
        this.commandInput.setOnAction(event -> this.submitCommand());

        HBox inputBar = new HBox(8, this.commandInput, this.sendButton);
        inputBar.setPadding(new Insets(8));
        HBox.setHgrow(this.commandInput, Priority.ALWAYS);

        BorderPane root = new BorderPane();
        root.setCenter(this.transcript);
        root.setBottom(inputBar);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle(WINDOW_TITLE);
        stage.setScene(scene);
        stage.show();

        try {
            this.martin = new Martin();
            this.transcript.setText(this.martin.getWelcomeMessage());
        } catch (MartinException exception) {
            this.transcript.setText("Martin could not start: " + exception.getMessage());
            this.commandInput.setDisable(true);
            this.sendButton.setDisable(true);
        }
    }

    /**
     * Processes the command currently entered in the input field.
     */
    private void submitCommand() {
        String input = this.commandInput.getText().trim();
        if (input.isEmpty() || this.martin == null || !this.martin.isRunning()) {
            return;
        }

        String response = this.martin.executeCommand(input);
        if (response.isEmpty() && !this.martin.isRunning()) {
            response = this.martin.getGoodbyeMessage();
        }
        this.transcript.appendText(System.lineSeparator() + System.lineSeparator()
                + "> " + input + System.lineSeparator() + response);
        this.commandInput.clear();

        if (!this.martin.isRunning()) {
            this.commandInput.setDisable(true);
            this.sendButton.setDisable(true);
            this.commandInput.getScene().getWindow().hide();
        }
    }
}
