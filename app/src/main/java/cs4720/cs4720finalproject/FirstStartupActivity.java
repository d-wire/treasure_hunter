package cs4720.cs4720finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cs4720.cs4720finalproject.R;
import cs4720.cs4720finalproject.Model.User;

public class FirstStartupActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernameKey = "usernameKey";
    public static final String iconKey = "iconKey";

    private EditText editText;
    private String username;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate widgets
        editText = (EditText) findViewById(R.id.editText);
        continueButton = (Button)findViewById(R.id.button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocally(editText.toString());
            }
        });

    }

    // Button onClick method to save the username and profile icon to the local database
    public void saveLocally(String username) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!editText.getText().equals("")) {
            username = editText.getText().toString();
        }
        editor.putString(usernameKey, username);
        Intent intent = new Intent(this, TriviaQuizActivity.class); // Will be removed later. Just to show that the web service works
        startActivity(intent); // Will be removed later. Just to show that the web service works

//        if(!editText.getText().equals("")) {
//            username = editText.getText().toString();
//            User me = new User(username);
//        }
    }
}
