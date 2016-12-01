package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cs4720.cs4720finalproject.R;
import cs4720.cs4720finalproject.Model.User;

public class FirstStartupActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    // Use to determine which icon image the user has selected


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernameKey = "usernameKey";
    public static final String iconKey = "iconKey";

    private EditText editText;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    private String username;
    private int imageSelected = 0;
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
                if (!editText.getText().toString().equals("")) {
                    Log.d("HELLO HELLO", editText.getText().toString());
                    saveLocally(editText.toString());
                } else {
                    Toast.makeText(FirstStartupActivity.this, "Must enter username",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                chooseImageSelected(1);
                                            }
                                        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                chooseImageSelected(2);
                                            }
                                        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                chooseImageSelected(3);
                                            }
                                        });
    }

    public void chooseImageSelected(int imagePressed) {
        imageSelected = imagePressed;
        if (imagePressed == 1) {
            imageButton1.setBackgroundResource(R.drawable.border);
            imageButton2.setBackgroundResource(0);
            imageButton3.setBackgroundResource(0);
        } else if (imagePressed == 2) {
            imageButton1.setBackgroundResource(0);
            imageButton2.setBackgroundResource(R.drawable.border);
            imageButton3.setBackgroundResource(0);
        } else {
            imageButton1.setBackgroundResource(0);
            imageButton2.setBackgroundResource(0);
            imageButton3.setBackgroundResource(R.drawable.border);
        }
    }

    // Button onClick method to save the username and profile icon to the local database
    public void saveLocally(String username) {
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            username = editText.getText().toString();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                putString(usernameKey, username).
                putString(iconKey, Integer.toString(imageSelected)).
                commit();

        //editor.putString(usernameKey, username);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }


    public static void hideKeyboard(Activity activity) {
        View v = activity.getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}
