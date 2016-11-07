package cs4720.cs4720finalproject.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public class TriviaQuiz {

    private ArrayList<QuizQuestion> results;

    public TriviaQuiz() {
        this.results = new ArrayList<QuizQuestion>();
    }

    public ArrayList<QuizQuestion> getQuizQuestions() {
        return results;
    }

    public void setQuizQuestions(ArrayList<QuizQuestion> quizQuestions) {
        this.results = quizQuestions;
    }


}
