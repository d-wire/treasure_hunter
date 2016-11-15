package cs4720.cs4720finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cs4720.cs4720finalproject.Model.QuizQuestion;
import cs4720.cs4720finalproject.Model.TriviaQuiz;
import cs4720.cs4720finalproject.Rest.ApiClient;
import cs4720.cs4720finalproject.Rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TriviaQuizActivity extends AppCompatActivity {

    public static final String TAG = TriviaQuizActivity.class.getSimpleName();
    private ArrayList<QuizQuestion> questions;
    private TextView textView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_quiz);

        textView = (TextView) findViewById(R.id.textView2);

        button = (Button)findViewById(R.id.newButton);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent intent = new Intent(TriviaQuizActivity.this, MapsActivity.class);
                                          startActivity(intent);
                                      }
                                  });


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TriviaQuiz> call = apiService.getQuizQuestions();
        call.enqueue(new Callback<TriviaQuiz>() {
            @Override
            public void onResponse(Call<TriviaQuiz> call, Response<TriviaQuiz> response) {
                questions = response.body().getResults();
                textView.setText(questions.toString());
                Log.d(TAG, "Number of quiz questions: " + questions.size());
            }

            @Override
            public void onFailure(Call<TriviaQuiz> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    public void reload(View v) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
