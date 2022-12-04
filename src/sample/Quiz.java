package sample;

import java.util.ArrayList;

public class Quiz {
    String question;
    ArrayList<String> answers = new ArrayList<String>();

    //Constructor
    public Quiz(String q, String a1, String a2, String a3, String a4){
        this.question = q;

        this.answers.add(a1);
        this.answers.add(a2);
        this.answers.add(a3);
        this.answers.add(a4);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
