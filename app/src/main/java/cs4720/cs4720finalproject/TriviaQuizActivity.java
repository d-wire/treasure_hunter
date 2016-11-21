package cs4720.cs4720finalproject;

import  android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

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
    private TextView questionBody;
    private int quizQuestionCounter = 0;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private ArrayList<Boolean> responses = new ArrayList<Boolean>();

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_quiz);

        questionBody = (TextView) findViewById(R.id.textView2);

        button1 = (Button) findViewById(R.id.newButton);
        buttons.add(button1);
        button2 = (Button) findViewById(R.id.button2);
        buttons.add(button2);
        button3 = (Button) findViewById(R.id.button3);
        buttons.add(button3);
        button4 = (Button) findViewById(R.id.button4);
        buttons.add(button4);

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("Quiz Difficulty");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if(difficulty.equals("Easy")) {
            Call<TriviaQuiz> call = apiService.getEasyQuizQuestions();
            call.enqueue(new Callback<TriviaQuiz>() {
                @Override
                public void onResponse(Call<TriviaQuiz> call, Response<TriviaQuiz> response) {
                    questions = response.body().getResults();
                    Log.d(TAG, "Number of quiz questions: " + questions.size());
                    nextQuestion();
                }

                @Override
                public void onFailure(Call<TriviaQuiz> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }

        else if(difficulty.equals("Medium")) {
            Call<TriviaQuiz> call = apiService.getMediumQuizQuestions();
            call.enqueue(new Callback<TriviaQuiz>() {
                @Override
                public void onResponse(Call<TriviaQuiz> call, Response<TriviaQuiz> response) {
                    questions = response.body().getResults();
                    Log.d(TAG, "Number of quiz questions: " + questions.size());
                    nextQuestion();
                }

                @Override
                public void onFailure(Call<TriviaQuiz> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }

        else if(difficulty.equals("Hard")) {
            Call<TriviaQuiz> call = apiService.getHardQuizQuestions();
            call.enqueue(new Callback<TriviaQuiz>() {
                @Override
                public void onResponse(Call<TriviaQuiz> call, Response<TriviaQuiz> response) {
                    questions = response.body().getResults();
                    Log.d(TAG, "Number of quiz questions: " + questions.size());
                    nextQuestion();
                }

                @Override
                public void onFailure(Call<TriviaQuiz> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }

    public void recordResponse(View v) {
        if(quizQuestionCounter >= (questions.size())) {
            //Will be replaced with starting quiz complete activity
            Log.d("Complete", "Quiz finished");
        }
        else {
            Button btn = (Button) v;
            String correctAnswer = questions.get(quizQuestionCounter).getCorrectAnswer();
            if(btn.getText().equals(correctAnswer)) {
                responses.add(true);
            }
            else {
                responses.add(false);
            }
            nextQuestion();
        }

    }

    public void nextQuestion() {
        String questionText = questions.get(quizQuestionCounter).getQuestion().replace("&quot;", "\"");
        questionText = questionText.replace("&#039;", "'");
        questionText = questionText.replace("&amp;", "&");
        ArrayList<String> answers = questions.get(quizQuestionCounter).getIncorrectAnswers();
        answers.add(questions.get(quizQuestionCounter).getCorrectAnswer());
        for(int i = 0; i < answers.size(); i++) {
            answers.get(i).replace("&quot;", "\"");
            answers.get(i).replace("&#039;", "'");
            answers.get(i).replace("&amp;", "&");
        }
        questionBody.setText(questionText);
        Collections.shuffle(answers);
        for(int i = 0; i < answers.size(); i++) {
            buttons.get(i).setText(answers.get(i));
        }
        if(answers.size() < buttons.size()) {
            for(int i = buttons.size() - answers.size(); i < buttons.size(); i++) {
                buttons.get(i).setVisibility(View.INVISIBLE);
            }
        }
        quizQuestionCounter += 1;
    }

    public void quit(View v) {
        finish();
    }

}
