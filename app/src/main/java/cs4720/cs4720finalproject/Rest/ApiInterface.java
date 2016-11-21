package cs4720.cs4720finalproject.Rest;

import cs4720.cs4720finalproject.Model.TriviaQuiz;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public interface ApiInterface {

    // Supposed to be used to get data from URL
    @GET("api.php?amount=5&difficulty=easy")
    Call<TriviaQuiz> getEasyQuizQuestions();

    @GET("https://www.opentdb.com/api.php?amount=5&difficulty=medium")
    Call<TriviaQuiz> getMediumQuizQuestions();

    @GET("https://www.opentdb.com/api.php?amount=5&difficulty=hard")
    Call<TriviaQuiz> getHardQuizQuestions();
}
