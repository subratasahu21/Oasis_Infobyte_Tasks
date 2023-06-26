package JavaFX.Task_3;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
class Transaction
{
    private String type;
    private double amount;
    public Transaction(String type, double amount)
    {
        this.type = type;
        this.amount = amount;
    }
    public String getType()
    {
        return type;
    }
    public double getAmount()
    {
        return amount;
    }
}
class Account
{
    private String userId;
    private int pin;
    private double balance;
    private List<Transaction> transactionHistory;
    public Account(String userId, int pin, double balance)
    {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }
    public boolean authenticate(String userId, int pin)
    {
        return this.userId.equals(userId) && this.pin == pin;
    }
    public void deposit(double amount)
    {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }
    public void withdraw(double amount)
    {
        if (balance >= amount)
        {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
        }
    }
    public void transfer(Account receiver, double amount)
    {
        if (balance >= amount)
        {
            balance -= amount;
            receiver.deposit(amount);
            transactionHistory.add(new Transaction("Transfer", amount));
        }
    }
    public void printTransactionHistory()
    {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory)
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
    }
    public double getBalance() 
    {
        return balance;
    }
}
public class ATMInterface extends Application 
{
    private Account account;
    private Label balanceLabel;
    private Label messageLabel;
    private TextField amountField;
    public static void main(String[] args)
    {
        launch(args);
    }
    public void start(Stage primaryStage)
    {
        account = new Account("user123", 1234, 1000.0);
        primaryStage.setTitle("ATM Interface");
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));
        Label titleLabel = new Label("ATM Interface");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        balanceLabel = new Label("Balance: $" + account.getBalance());
        balanceLabel.setStyle("-fx-font-size: 16px;");
        messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        HBox amountBox = new HBox(10);
        amountBox.setAlignment(Pos.CENTER);
        Label amountLabel = new Label("Amount:");
        amountField = new TextField();
        amountBox.getChildren().addAll(amountLabel, amountField);
        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(e -> depositClicked());
        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(e -> withdrawClicked());
        Button transferButton = new Button("Transfer");
        transferButton.setOnAction(e -> transferClicked());
        Button historyButton = new Button("Transaction History");
        historyButton.setOnAction(e -> historyClicked());
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> primaryStage.close());
        vBox.getChildren().addAll(
                titleLabel, balanceLabel, messageLabel,
                amountBox, depositButton, withdrawButton,
                transferButton, historyButton, quitButton
        );
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        vBox.setBackground(background);
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void depositClicked()
    {
        String amountText = amountField.getText();
        if (!amountText.isEmpty()) 
        {
            try 
            {
                double amount = Double.parseDouble(amountText);
                account.deposit(amount);
                updateBalance();
                clearFields();
            } 
            catch (NumberFormatException e) 
            {
                displayError("Invalid amount format.");
            }
        } 
        else
            displayError("Please enter an amount.");
    }
    private void withdrawClicked()
    {
        String amountText = amountField.getText();
        if (!amountText.isEmpty())
        {
            try 
            {
                double amount = Double.parseDouble(amountText);
                account.withdraw(amount);
                updateBalance();
                clearFields();
            } 
            catch (NumberFormatException e) 
            {
                displayError("Invalid amount format.");
            }
        } 
        else
            displayError("Please enter an amount.");
    }
    private void transferClicked()
    {
        String amountText = amountField.getText();
        if (!amountText.isEmpty()) 
        {
            try
            {
                double amount = Double.parseDouble(amountText);
                Account receiver = new Account("receiver", 1234, 0);
                account.transfer(receiver, amount);
                updateBalance();
                clearFields();
            } 
            catch (NumberFormatException e) 
            {
                displayError("Invalid amount format.");
            }
        } 
        else
            displayError("Please enter an amount.");
    }
    private void historyClicked() 
    {
        account.printTransactionHistory();
    }
    private void updateBalance() 
    {
        balanceLabel.setText("Balance: $" + account.getBalance());
    }
    private void displayError(String message) 
    {
        messageLabel.setText(message);
    }
    private void clearFields() 
    {
        amountField.clear();
        messageLabel.setText("");
    }
}