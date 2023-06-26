package JavaFX.Task_2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Random;
public class NumberGuessingGame extends Application 
{
    private Random random;
    private int targetNumber;
    private int attempts;
    private Label messageLabel;
    private TextField guessTextField;
    public NumberGuessingGame() 
    {
        random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
    }
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("Number Guessing Game");
        messageLabel = new Label("Welcome to the Number Guessing Game!");
        messageLabel.setFont(Font.font("Arial", 20));
        messageLabel.setTextFill(Color.WHITE);
        Label instructionLabel = new Label("Enter your guess (between 1 and 100):");
        instructionLabel.setFont(Font.font("Arial", 16));
        instructionLabel.setTextFill(Color.WHITE);
        guessTextField = new TextField();
        guessTextField.setStyle("-fx-font-size: 16px;");
        Button guessButton = new Button("Guess");
        guessButton.setStyle("-fx-font-size: 16px;");
        guessButton.setOnAction(e -> checkGuess());
        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 16px;");
        restartButton.setOnAction(e -> restartGame());
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(messageLabel, instructionLabel, guessTextField, guessButton, restartButton);
        Scene scene = new Scene(root, 800, 600);
        scene.setFill(Color.LAVENDER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void checkGuess()
    {
        String guessText = guessTextField.getText();
        try
        {
            int guess = Integer.parseInt(guessText);
            attempts++;
            if (guess < targetNumber) 
            {
                messageLabel.setText("Too low! Try again.");
                messageLabel.setTextFill(Color.RED);
            } 
            else if (guess > targetNumber) 
            {
                messageLabel.setText("Too high! Try again.");
                messageLabel.setTextFill(Color.RED);
            } 
            else 
            {
                messageLabel.setText("Congratulations! You guessed the number correctly in " + attempts + " attempts.");
                messageLabel.setTextFill(Color.GREEN);
                guessTextField.setEditable(false);
            }
            guessTextField.clear();
            guessTextField.requestFocus();
        } 
        catch (NumberFormatException e)
        {
            messageLabel.setText("Invalid input! Please enter a valid integer.");
            messageLabel.setTextFill(Color.RED);
        }
    }
    private void restartGame() 
    {
        targetNumber = random.nextInt(100) + 1;
        attempts = 0;
        messageLabel.setText("Welcome to the Number Guessing Game!");
        messageLabel.setTextFill(Color.WHITE);
        guessTextField.clear();
        guessTextField.setEditable(true);
        guessTextField.requestFocus();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}