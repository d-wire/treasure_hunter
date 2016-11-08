package cs4720.cs4720finalproject.Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public class TriviaQuiz {

    // Defines what the variable is called in the parsed JSON object
    @SerializedName("results")
    private ArrayList<QuizQuestion> results;

    public TriviaQuiz() {
        this.results = new ArrayList<QuizQuestion>();
    }

    public ArrayList<QuizQuestion> getResults() {
        return results;
    }

    public void setResults(ArrayList<QuizQuestion> quizQuestions) {
        this.results = quizQuestions;
    }


}
