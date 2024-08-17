import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class WordleGame extends Application {
    static class Player {
        String name;
        int yearJoined;
        String position;
        int number;
        String nationality;

        public Player(String name, int yearJoined, String position, int number, String nationality) {
            this.name = name;
            this.yearJoined = yearJoined;
            this.position = position;
            this.number = number;
            this.nationality = nationality;
        }
    }

    private static Player[] players = {
        new Player("AJ Marcucci", 2021, "Goalkeeper", 1, "United States"),
        new Player("Carlos Coronel", 2021, "Goalkeeper", 31, "Brazil"),
        new Player("Ryan Meara", 2012, "Goalkeeper", 18, "United States"),
        new Player("Aiden Stokes", 2024, "Goalkeeper", 21, "United States"),
        new Player("Davi Alexandre", 2024, "Defender", 27, "United States"),
        new Player("Kyle Duncan", 2018, "Defender", 6, "United States"),
        new Player("Noah Eile", 2024, "Defender", 2, "Sweden"),
        new Player("Juan Mina", 2023, "Defender", 20, "Colombia"),
        new Player("Dylan Nealis", 2022, "Defender", 12, "United States"),
        new Player("Sean Nealis", 2019, "Defender", 15, "United States"),
        new Player("Aiden O'Connor", 2024, "Defender", 23, "United States"),
        new Player("Curtis Ofori", 2023, "Defender", 24, "United States"),
        new Player("Andres Reyes", 2021, "Defender", 4, "Colombia"),
        new Player("John Tolkin", 2020, "Defender", 47, "United States"),
        new Player("Frankie Amaya", 2021, "Midfielder", 8, "United States"),
        new Player("Wikelman Carmona", 2021, "Midfielder", 19, "United States"),
        new Player("Ronald Donkor", 2023, "Midfielder", 48, "Ghana"),
        new Player("Daniel Edelman", 2022, "Midfielder", 75, "United States"),
        new Player("Bento Estrela", 2024, "Midfielder", 91, "United States"),
        new Player("Emil Forsberg", 2024, "Midfielder", 10, "Sweden"),
        new Player("Cameron Harper", 2021, "Midfielder", 17, "United States"),
        new Player("Lewis Morgan", 2022, "Midfielder", 9, "Scotland"),
        new Player("Serge Ngoma", 2022, "Midfielder", 22, "United States"),
        new Player("Peter Stroud", 2023, "Midfielder", 5, "United States"),
        new Player("Dennis Gjengaar", 2024, "Midfielder", 2, "Norway"),
        new Player("Cory Burke", 2023, "Forward", 7, "Jamaica"),
        new Player("Julian Hall", 2024, "Forward", 16, "United States"),
        new Player("Elias Manoel", 2022, "Forward", 11, "Brazil"),
        new Player("Roald Mitchell", 2024, "Forward", 33, "United States"),
        new Player("Dante Vanzeir", 2023, "Forward", 13, "Belgium")
    };

    private static Player targetPlayer;
    private static final int MAX_ATTEMPTS = 5;
    private int attempts = 0;
    private ListView<String> historyListView;
    private TextField yearInput;
    private TextField positionInput;
    private TextField numberInput;
    private TextField nationalityInput;

    public static void main(String[] args) {
        Random random = new Random();
        targetPlayer = players[random.nextInt(players.length)];
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Red Bull Wordle");

        Image icon = new Image(getClass().getResourceAsStream("RBNY.png"));
        primaryStage.getIcons().add(icon);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label yearLabel = new Label("Year Joined:");
        GridPane.setConstraints(yearLabel, 0, 0);
        yearInput = new TextField();
        yearInput.setId("yearInput");
        GridPane.setConstraints(yearInput, 1, 0);

        Label positionLabel = new Label("Position:");
        GridPane.setConstraints(positionLabel, 0, 1);
        positionInput = new TextField();
        positionInput.setId("positionInput");
        GridPane.setConstraints(positionInput, 1, 1);

        Label numberLabel = new Label("Number:");
        GridPane.setConstraints(numberLabel, 0, 2);
        numberInput = new TextField();
        numberInput.setId("numberInput");
        GridPane.setConstraints(numberInput, 1, 2);

        Label nationalityLabel = new Label("Nationality:");
        GridPane.setConstraints(nationalityLabel, 0, 3);
        nationalityInput = new TextField();
        nationalityInput.setId("nationalityInput");
        GridPane.setConstraints(nationalityInput, 1, 3);

        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 1, 4);

        Button hintButton = new Button("Hint");
        GridPane.setConstraints(hintButton, 1, 5);

        Label feedbackLabel = new Label();
        GridPane.setConstraints(feedbackLabel, 1, 6);

        historyListView = new ListView<>();
        GridPane.setConstraints(historyListView, 0, 7, 2, 1);

        submitButton.setOnAction(e -> {
            int year = Integer.parseInt(yearInput.getText());
            String position = positionInput.getText();
            int number = Integer.parseInt(numberInput.getText());
            String nationality = nationalityInput.getText();

            boolean yearCorrect = year == targetPlayer.yearJoined;
            boolean positionCorrect = position.equalsIgnoreCase(targetPlayer.position);
            boolean numberCorrect = number == targetPlayer.number;
            boolean nationalityCorrect = nationality.equalsIgnoreCase(targetPlayer.nationality);

            String feedback = "Year Joined: " + (yearCorrect ? "Y" : "X") +
                    ", Position: " + (positionCorrect ? "Y" : "X") +
                    ", Number: " + (numberCorrect ? "Y" : "X") +
                    ", Nationality: " + (nationalityCorrect ? "Y" : "X");

            historyListView.getItems().add(feedback);

            if (yearCorrect && positionCorrect && numberCorrect && nationalityCorrect) {
                feedbackLabel.setText("Correct! The player is " + targetPlayer.name + ".");
                submitButton.setDisable(true);
                hintButton.setDisable(true);
            } else {
                feedbackLabel.setText("Attempts left: " + (MAX_ATTEMPTS - ++attempts));
                if (attempts >= MAX_ATTEMPTS) {
                    feedbackLabel.setText("Out of attempts! The player was " + targetPlayer.name + " from " + targetPlayer.nationality + ".");
                    submitButton.setDisable(true);
                    hintButton.setDisable(true);
                }
            }
        });

        hintButton.setOnAction(e -> {
            if (attempts < MAX_ATTEMPTS) {
                String hint = getHint();
                feedbackLabel.setText(hint);
                hintButton.setDisable(true);
            }
        });

        grid.getChildren().addAll(yearLabel, yearInput, positionLabel, positionInput, numberLabel, numberInput, nationalityLabel, nationalityInput, submitButton, hintButton, feedbackLabel, historyListView);

        Scene scene = new Scene(grid, 400, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String getHint() {
        boolean yearCorrect = yearInput.getText().equals(String.valueOf(targetPlayer.yearJoined));
        boolean positionCorrect = positionInput.getText().equalsIgnoreCase(targetPlayer.position);
        boolean numberCorrect = numberInput.getText().equals(String.valueOf(targetPlayer.number));
        boolean nationalityCorrect = nationalityInput.getText().equalsIgnoreCase(targetPlayer.nationality);

        Random random = new Random();
        int[] attributes = {0, 1, 2, 3};
        boolean[] correct = {yearCorrect, positionCorrect, numberCorrect, nationalityCorrect};

        String hint = "";

        while (hint.isEmpty()) {
            int index = random.nextInt(attributes.length);
            if (!correct[index]) {
                switch (index) {
                    case 0:
                        hint = "Hint: The player joined in " + targetPlayer.yearJoined;
                        break;
                    case 1:
                        hint = "Hint: The player's position is " + targetPlayer.position;
                        break;
                    case 2:
                        hint = "Hint: The player's number is " + targetPlayer.number;
                        break;
                    case 3:
                        hint = "Hint: The player's nationality is " + targetPlayer.nationality;
                        break;
                }
            }
        }
        return hint;
    }
}
