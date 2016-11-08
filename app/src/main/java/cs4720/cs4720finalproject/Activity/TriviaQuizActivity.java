package cs4720.cs4720finalproject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import cs4720.cs4720finalproject.Model.QuizQuestion;
import cs4720.cs4720finalproject.Model.TriviaQuiz;
import cs4720.cs4720finalproject.R;
import cs4720.cs4720finalproject.Rest.ApiClient;
import cs4720.cs4720finalproject.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TriviaQuizActivity extends AppCompatActivity {

    public static final String TAG = TriviaQuizActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_quiz);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TriviaQuiz> call = apiService.getQuizQuestions();
        call.enqueue(new Callback<TriviaQuiz>() {
            @Override
            public void onResponse(Call<TriviaQuiz> call, Response<TriviaQuiz> response) {
                ArrayList<QuizQuestion> questions = response.body().getResults();
                Log.d(TAG, "Number of quiz questions: " + questions.size());
            }

            @Override
            public void onFailure(Call<TriviaQuiz> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}
