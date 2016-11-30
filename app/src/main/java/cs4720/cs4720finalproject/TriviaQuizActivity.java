package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Context;
import  android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs4720.cs4720finalproject.Model.EasyTreasureChest;
import cs4720.cs4720finalproject.Model.QuizQuestion;
import cs4720.cs4720finalproject.Model.TreasureChest;
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
    private TreasureChest chest;
    private static final int QUIZ_SUCCESS = 2;
    private static final int QUIZ_FAILED = 3;


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

        for(int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra("Quiz Difficulty");
        Bundle bundle = intent.getBundleExtra("sent chest");
        chest = (TreasureChest) bundle.getSerializable("chest");

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
            Button btn = (Button) v;
            String correctAnswer = questions.get(quizQuestionCounter - 1).getCorrectAnswer();
            Log.d("btn", "" + btn.getText());
            Log.d("correct answer", "" + correctAnswer);
            if(btn.getText().equals(correctAnswer)) {
                responses.add(true);
            }
            else {
                responses.add(false);
            }
            int counter = 0;
            for(int i = 0; i < responses.size(); i++) {
                if(responses.get(i) == true) {
                    counter += 1;
                }
            }
            Log.d("Complete", "Quiz finished: " + counter);
            Log.d("Counter", "" + responses);
            //if(counter >= 3) {
                TinyDB tinyDB = new TinyDB(getApplicationContext());
                ArrayList<String> items = tinyDB.getListString("allItems");
                if(items != null) {
                    items.addAll(chest.getItemList());
                    tinyDB.putListString("allItems", items);
                }
                else {
                    tinyDB.putListString("allItems", chest.getItemList());
                }
                /*SharedPreferences readSharedPref = getSharedPreferences("items", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = readSharedPref.getString("key", null);
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> allItems = gson.fromJson(json, type);
                Log.d("All items", "" + allItems);
                if(allItems != null) {
                    ArrayList<String> chestItems = chest.getItemList();
                    allItems.addAll(chestItems);
                    String json2 = gson.toJson(allItems);
                    SharedPreferences.Editor editor = readSharedPref.edit();
                    editor.putString("key", json);
                    editor.commit();
                }
                else {
                    String json2 = gson.toJson(chest.getItemList());
                    SharedPreferences.Editor editor = readSharedPref.edit();
                    editor.putString("key", json);
                    editor.commit();
                }*/

                Intent nextActivity = new Intent(TriviaQuizActivity.this, QuizCompleteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("chest", chest);
                nextActivity.putExtra("sent chest", bundle);
                startActivityForResult(nextActivity, QUIZ_SUCCESS);
           // }
            //else {
                //Intent nextActivity = new Intent(TriviaQuizActivity.this, QuizFailedActivity.class);
                //startActivityForResult(nextActivity, QUIZ_FAILED);
           // }

            /*Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();*/
        }
        else {
            Button btn = (Button) v;
            String correctAnswer = questions.get(quizQuestionCounter - 1).getCorrectAnswer();
            Log.d("btn", "" + btn.getText());
            Log.d("correct answer", "" + correctAnswer);
            if(btn.getText().equals(correctAnswer)) {
                responses.add(true);
            }
            else {
                responses.add(false);
            }
            for(int i = 0; i < buttons.size(); i++) {
                buttons.get(i).setVisibility(View.GONE);
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
        Log.d("Answers", "" + answers);
        for(int i = 0; i < answers.size(); i++) {
            answers.get(i).replace("&quot;", "\"");
            answers.get(i).replace("&#039;", "'");
            answers.get(i).replace("&amp;", "&");
        }
        questionBody.setText(questionText);
        Collections.shuffle(answers);
        for(int i = 0; i < answers.size(); i++) {
            buttons.get(i).setVisibility(View.VISIBLE);
            buttons.get(i).setText(answers.get(i));
        }
        /*if(answers.contains("True") && answers.contains("False")) {
            for(int i = 2; i < 5; i++) {
                buttons.get(i).setVisibility(View.INVISIBLE);
            }
        }*/
        quizQuestionCounter += 1;
    }

    public void quit(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == QUIZ_SUCCESS || requestCode == QUIZ_FAILED) {
            if(resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
    }

}
