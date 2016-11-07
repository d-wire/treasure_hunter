package cs4720.cs4720finalproject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs4720.cs4720finalproject.R;

public class TriviaQuizActivity extends AppCompatActivity {

    public static final String TAG = TriviaQuizActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_quiz);
    }
}
