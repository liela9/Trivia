package sample;

import java.util.ArrayList;

public class Trivia {
    int score;
    ArrayList<Quiz> quiz;

    //Constructor
    public Trivia() {
        score = 0;
        quiz = new ArrayList<Quiz>();
    }

    public void rightGuess(){
        score += 10;
    }

    public void wrongGuess(){
        score -= 5;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Quiz> getQuiz() {
        return quiz;
    }

    public void setQuiz(ArrayList<Quiz> quiz) {
        this.quiz = quiz;
    }
}
