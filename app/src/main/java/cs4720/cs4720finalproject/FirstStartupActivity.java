package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import cs4720.cs4720finalproject.R;
import cs4720.cs4720finalproject.Model.User;

public class FirstStartupActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernameKey = "usernameKey";
    public static final String iconKey = "iconKey";

    private EditText editText;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    private String username;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate widgets
        editText = (EditText) findViewById(R.id.editText);
        continueButton = (Button)findViewById(R.id.button);
        imageButton1 = (ImageButton)findViewById(R.id.imageButton);
        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton)findViewById(R.id.imageButton3);


        // when user clicks on the "continue button," save necessary data locally
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocally(editText.toString());
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });

//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    Log.d("owen", "in");
//                    hideKeyboard();
//                }
//                Log.d("focus", String.valueOf(hasFocus));
//                Log.d("owen", "out");
//                hideKeyboard();
//            }
//        });

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

//    public void hideKeyboard() {
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//    }

    public static void hideKeyboard(Activity activity) {
        View v = activity.getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
