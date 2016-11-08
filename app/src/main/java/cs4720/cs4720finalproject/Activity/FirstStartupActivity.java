package cs4720.cs4720finalproject.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import cs4720.cs4720finalproject.R;
import cs4720.cs4720finalproject.Model.User;

public class FirstStartupActivity extends AppCompatActivity {

    private EditText editText;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }

    // Button onClick method to save the username and profile icon to the local database
    public void saveToDB() {
        if(!editText.getText().equals("")) {
            username = editText.getText().toString();
            User me = new User(username);
        }
    }
}
