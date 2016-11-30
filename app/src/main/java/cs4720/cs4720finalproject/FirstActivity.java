package cs4720.cs4720finalproject;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FirstActivity extends AppCompatActivity {

    public static final String usernameKey = "usernameKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                getString(usernameKey, "defaultStringIfNothingFound");

        if (!username.equals("defaultStringIfNothingFound")) {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, FirstStartupActivity.class);
            startActivity(intent);
        }
    }
}
