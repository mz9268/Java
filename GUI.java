import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class GUI extends Application {
    // quiz variables
    private int currentQuestionIndex = 0; //checks the current question index
    private int score = 0; //stores the score of the user
    private List<String> questions = new ArrayList<>();
    private List<String[]> options = new ArrayList<>();
    private List<Integer> correctAnswers = new ArrayList<>();
    private Label question;
    private Label scores;
    private TextField username;
    private VBox optionsBox = new VBox(5);

    private int totalTimeLeft = 30; //total time for the quiz is 30 secs
    private Label timerLabel = new Label("Time left: 30");
    private Timeline totalTimer;

    /**
     * this sets up the window screen and has user input for username
     */
    @Override
    public void start(Stage stage) throws Exception {
        loadQuestionsFromFile("questions.txt");

        username = new TextField("Enter Username");
        Button start = new Button("Start!");
        Label welcomeText = new Label("Welcome to the quiz"); //welcome text
        Label rules = new Label("Rules: \n1. Answer all the questions\n2. As you select an option, it will take you to the next question\n3. After attempting all the questions, submit your work and it will show your scores and the leaderboard."); //rules of the quiz
        Label noUsername = new Label("");
        //start button action
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String input = username.getText().trim();
                if (input.isEmpty()) {
                    noUsername.setText("Cannot start, please enter a username.");
                } else {
                    startQuiz(stage);
                }
            }
        });
        //layout of the welcome screen, with welcome text,username input, rules,and then the start button
        VBox startLayout = new VBox(10, welcomeText, username, rules, noUsername, start);
        Scene startScene = new Scene(startLayout, 600, 500);
        stage.setScene(startScene);
        stage.setTitle("Quiz");// name of the window
        stage.show();
    }

    /*
     * this method is used to extract questions from the file name questions.txt which contains, questions, options an dthe correct answer for the score
     */
    private void loadQuestionsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File("C:\\Users\\Rishika\\Documents\\gcis124-604\\assignment2.java\\questions.txt"))) {
            while (scanner.hasNextLine()) {
                String questionText = scanner.nextLine().trim();
                if (questionText.isEmpty()) continue;

                String[] optionsText = scanner.hasNextLine() ? scanner.nextLine().trim().split(",") : new String[0];
                int correctIndex = scanner.hasNextLine() ? Integer.valueOf(scanner.nextLine().trim()) : -1;

                if (optionsText.length > 0 && correctIndex >= 0) {
                    questions.add(questionText);
                    options.add(optionsText);
                    correctAnswers.add(correctIndex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * starts the quiz after user inputs their username
     */
    private void startQuiz(Stage stage) {
        question = new Label();
        scores = new Label("Score: 0");
        Button submit = new Button("Submit");
    
        // Use normal EventHandler instead of lambda
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                submitScore(); // Call submitScore() method
            }
        });
    
        // Layout of the quiz screen
        VBox quizLayout = new VBox(10, question, optionsBox, timerLabel, scores, submit);
        Scene quizScene = new Scene(quizLayout, 300, 400);
        stage.setScene(quizScene);
    
        startTotalTimer(); // 30secs timer
        loadNextQuestion();
    }
    

    private void startTotalTimer() {
        totalTimeLeft = 30; // Reset timer
        timerLabel.setText("Time left: " + totalTimeLeft + " s");

        totalTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            totalTimeLeft--;
            timerLabel.setText("Time left: " + totalTimeLeft + " s");

            if (totalTimeLeft <= 0) {
                totalTimer.stop();
                endQuiz(); //end the quiz when time is up
            }
        }));

        totalTimer.setCycleCount(30);
        totalTimer.play();
    }

    /*
     * loads the next question once the user has selected the option
     */
    private void loadNextQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            endQuiz();
            return;
        }
    
        question.setText(questions.get(currentQuestionIndex));
        optionsBox.getChildren().clear();
        // options are displayed as buttons
        for (int i = 0; i < options.get(currentQuestionIndex).length; i++) {
            final int optionIndex = i;  // Capture the current value of i in a final variable
            Button optionButton = new Button(options.get(currentQuestionIndex)[i]);
            
            // Use a traditional EventHandler instead of a lambda expression
            optionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    checkAnswer(optionIndex);  // Use the captured optionIndex
                    loadNextQuestion();
                }
            });
            
            optionsBox.getChildren().add(optionButton);
        }
    }
    

    /*
     * this checks if the user has selected the correct option and it matches with the correct option given in the txt file
     */
    private void checkAnswer(int selectedIndex) {
        if (selectedIndex == correctAnswers.get(currentQuestionIndex)) {
            score++;
            scores.setText("Score: " + score);
        }
        currentQuestionIndex++;
        loadNextQuestion();
    }

    /*
     * submits the final score
     */
    private void submitScore() {
        String usernameText = username.getText();
        saveScoreToFile(usernameText, score);

        // Show leaderboard after submitting score
        Stage stage = (Stage) scores.getScene().getWindow(); // Get current stage
        showLeaderboard(stage);
    }

    /*
     * saves the users score to leaderboard
     */
    private void saveScoreToFile(String username, int score) {
        try {
            File file = new File("leaderboard.txt");
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(username + " - " + score);
            bufferedWriter.newLine();
            bufferedWriter.close();
            System.out.println("Score submitted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the leaderboard with scores sorted in descending order.
     * @param stage
     */
    private void showLeaderboard(Stage stage) {
        List<String> leaderboardEntries = readLeaderboard();
        VBox leaderboardBox = new VBox(5);
        Label leaderboardTitle = new Label("Leaderboard");
    
        // Display top 5 scores
        for (String entry : leaderboardEntries) {
            leaderboardBox.getChildren().add(new Label(entry));
        }
    
        Button exitButton = new Button("Exit");
    
        // Use a normal EventHandler instead of a lambda
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit(); // Handle exit action
            }
        });
    
        leaderboardBox.getChildren().add(exitButton);
        Scene leaderboardScene = new Scene(leaderboardBox, 300, 400);
        stage.setScene(leaderboardScene);
    }
    

    /**
     * Reads the leaderboard from the file, sorts it in descending order, 
     * and returns the top 5 scores.
     * 
     * @return a list of top 5 leaderboard entries.
     */
    private List<String> readLeaderboard() {
        List<String> scoresList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("leaderboard.txt"))) {
            while (scanner.hasNextLine()) {
                scoresList.add(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sort scores manually in descending order without lambda
        for (int i = 0; i < scoresList.size(); i++) {
            for (int j = i + 1; j < scoresList.size(); j++) {
                String[] score1 = scoresList.get(i).split(" - ");
                String[] score2 = scoresList.get(j).split(" - ");
                int score1Value = Integer.parseInt(score1[1]);
                int score2Value = Integer.parseInt(score2[1]);

                // Swap if score1 is less than score2
                if (score1Value < score2Value) {
                    String temp = scoresList.get(i);
                    scoresList.set(i, scoresList.get(j));
                    scoresList.set(j, temp);
                }
            }
        }

        // Return top 5 or fewer if not enough entries
        return scoresList.size() > 5 ? scoresList.subList(0, 5) : scoresList;
    }

    /*
     * endquiz-if the time is over or when the questions are completed
     */
    private void endQuiz() {
        question.setText("Time's up! Quiz Completed!");
        optionsBox.getChildren().clear();
    }

    /*
     * main method to launch the application
     */    
    public static void main(String[] args) {
        launch(args);
    }
}
