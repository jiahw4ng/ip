package martin.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import martin.core.Martin;
import martin.exception.MartinException;
import martin.ui.Ui;

/**
 * A JavaFX application that provides a graphical interface for Martin.
 */
public class Main extends Application {

    private static final String WINDOW_TITLE = "Martin";
    private static final double WINDOW_WIDTH = 600;
    private static final double WINDOW_HEIGHT = 450;
    private static final double MAX_MESSAGE_WIDTH = 480;
    private static final double MESSAGE_HORIZONTAL_PADDING = 24;
    private static final double MESSAGE_VERTICAL_PADDING = 16;

    private Martin martin;
    private VBox messages;
    private ScrollPane transcript;
    private TextField commandInput;
    private Button sendButton;

    /**
     * Creates and displays the application's primary window.
     *
     * @param stage The primary stage provided by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        this.messages = new VBox(10);
        this.messages.setPadding(new Insets(12));

        this.transcript = new ScrollPane(this.messages);
        this.transcript.setFitToWidth(true);
        this.transcript.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.transcript.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

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
            this.appendMartinMessage(Ui.MARTIN_GREETING);
        } catch (MartinException exception) {
            this.appendMartinMessage("Martin could not start: " + exception.getMessage());
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
        this.appendUserMessage(input);
        this.appendMartinMessage(response);
        this.commandInput.clear();

        if (!this.martin.isRunning()) {
            this.commandInput.getScene().getWindow().hide();
        }
    }

    /**
     * Adds a user command to the chat transcript.
     *
     * @param input The command entered by the user.
     */
    private void appendUserMessage(String input) {
        this.appendMessage("> " + input, Pos.CENTER_RIGHT, Color.LIGHTBLUE);
    }

    /**
     * Adds a Martin response to the chat transcript.
     *
     * @param message The response from Martin.
     */
    private void appendMartinMessage(String message) {
        this.appendMessage(message, Pos.CENTER_LEFT, Color.LIGHTGRAY);
    }

    /**
     * Adds a text message inside a rectangular chat bubble.
     *
     * @param message The message to display.
     * @param alignment The side of the chat area on which to place the message.
     * @param color The fill color of the message rectangle.
     */
    private void appendMessage(String message, Pos alignment, Color color) {
        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        double messageContentWidth = MAX_MESSAGE_WIDTH - MESSAGE_HORIZONTAL_PADDING;
        messageLabel.setPrefWidth(messageContentWidth);
        messageLabel.setMaxWidth(messageContentWidth);

        Rectangle messageRectangle = new Rectangle(MAX_MESSAGE_WIDTH, MESSAGE_VERTICAL_PADDING);
        messageRectangle.setArcWidth(12);
        messageRectangle.setArcHeight(12);
        messageRectangle.setFill(color);
        messageRectangle.heightProperty().bind(messageLabel.heightProperty().add(MESSAGE_VERTICAL_PADDING));

        StackPane messageBubble = new StackPane(messageRectangle, messageLabel);
        HBox messageRow = new HBox(messageBubble);
        messageRow.setAlignment(alignment);
        this.messages.getChildren().add(messageRow);
    }
}
