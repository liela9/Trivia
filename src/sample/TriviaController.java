package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Scanner;

public class TriviaController {

    @FXML
    private Button endGameBtn;
    @FXML
    private Button submitBtn;
    @FXML
    private Label questionLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private RadioButton radioBtn1;
    @FXML
    private RadioButton radioBtn2;
    @FXML
    private RadioButton radioBtn3;
    @FXML
    private RadioButton radioBtn4;

    private ToggleGroup g;
    private Trivia game;
    private String currentAnswer;
    private final int NO_OF_QUESTIONS = 10;
    private int count;

    public void initialize()  {
        welcomeLabel.setText("Welcome To My Trivia");

        g = new ToggleGroup();
        radioBtn1.setToggleGroup(g);
        radioBtn2.setToggleGroup(g);
        radioBtn3.setToggleGroup(g);
        radioBtn4.setToggleGroup(g);

        setGame();
    }

    /**
     * This method sets new game.
     */
    private void setGame() {
        game = new Trivia();
        endGameBtn.setVisible(false);
        submitBtn.setVisible(false);
    }

    /**
     * This method sets a new game.
     * Executes when the user presses 'Start New Game'.
     */
    @FXML
    void startPressed(ActionEvent event) {
        //set screen visibility
        welcomeLabel.setVisible(false);
        questionLabel.setVisible(true);
        radioBtn1.setVisible(true);
        radioBtn2.setVisible(true);
        radioBtn3.setVisible(true);
        radioBtn4.setVisible(true);
        endGameBtn.setVisible(true);
        submitBtn.setVisible(true);

        scanInputQuestions();// reinitialize the questions array
        game.setScore(0); // reset the score of the game
        count = 0; // reset the questions counter
        scoreLabel.setText("Your score: " + game.getScore());
        presentNewQuestion();
    }

    /**
     * This method handles the user selection.
     * Executes when the user presses 'Submit', after choosing an answer.
     */
    @FXML
    void submitPressed(ActionEvent event) {
         RadioButton selected = (RadioButton)g.getSelectedToggle();

         // nothing selected
         if(selected == null){
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Warning");
             alert.setHeaderText("Warning");
             alert.setContentText("Missing selection!");
             alert.showAndWait();
         }
         // some radio button selected
         else{
             if(selected.getText().equals(currentAnswer))
                 rightAnswer();
             else wrongAnswer();

             game.quiz.remove(0); // remove the question from the list (prevent repetition)
             presentNewQuestion();

         }
         scoreLabel.setText("Your score: " + game.getScore());//update the score that presents to the user
    }

    /**
     * This method handles the situation that the user pressed 'End Game'.
     */
    @FXML
    void endPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit");
        alert.setContentText("Are you sure you want to exit the game?");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK)
            endGame();
    }

    /**
     * This method ends the current game.
     */
    private void endGame(){
        //set clear screen
        questionLabel.setVisible(false);
        radioBtn1.setVisible(false);
        radioBtn2.setVisible(false);
        radioBtn3.setVisible(false);
        radioBtn4.setVisible(false);
        scoreLabel.setText("");

        gameSummary();//presents a summary of ended game
        setGame();//resets the game parameters
    }

    /**
     * This method presents the user the next question.
     */
    private void presentNewQuestion(){
        count++;
        if(count > NO_OF_QUESTIONS){
            endGame();
            return;
        }

        //reset selected button
        radioBtn1.setSelected(false);
        radioBtn2.setSelected(false);
        radioBtn3.setSelected(false);
        radioBtn4.setSelected(false);

        Quiz q = game.quiz.get(0);
        currentAnswer = q.getAnswers().get(0);//save the current right answer

        questionLabel.setText(q.getQuestion());

        Collections.shuffle(q.getAnswers()); // shuffle the answers order
        radioBtn1.setText(q.getAnswers().get(0));
        radioBtn2.setText(q.getAnswers().get(1));
        radioBtn3.setText(q.getAnswers().get(2));
        radioBtn4.setText(q.getAnswers().get(3));
    }

    /**
     * This method scans all the questions from the file named 'trivia.txt'.
     * It saves them into the array 'quiz'.
     */
    private void scanInputQuestions(){
        game.quiz = new ArrayList<Quiz>();;
        Scanner input = null;
        String question, rightAns, wrongAns1, wrongAns2, wrongAns3;

        try {
            input = new Scanner(new File("trivia.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // read line by line - set the array of the questions
        while (input.hasNext()){
            question = input.nextLine();
            rightAns = input.nextLine();
            wrongAns1 = input.nextLine();
            wrongAns2 = input.nextLine();
            wrongAns3 = input.nextLine();
            Quiz quiz = new Quiz(question, rightAns, wrongAns1, wrongAns2, wrongAns3);
            game.quiz.add(quiz);
        }
        input.close();
    }

    /**
     * This method handles the case of wrong answer.
     */
    private void wrongAnswer(){
        game.wrongGuess();// update the game's score

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong");
        alert.setHeaderText("");
        alert.setContentText("Wrong Answer");
        alert.showAndWait();
    }

    /**
     * This method handles the case of correct answer.
     */
    private void rightAnswer(){
        game.rightGuess(); // update the game's score

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Correct");
        alert.setHeaderText("");
        alert.setContentText("Correct Answer!");
        alert.showAndWait();
    }

    /**
     * This method summaries the user final score.
     */
    private void gameSummary(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Summary");
        alert.setHeaderText("");
        alert.setContentText("Your final score is: " + game.getScore());
        alert.showAndWait();
    }
}
