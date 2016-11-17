package cs4720.cs4720finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HomePageActivity extends AppCompatActivity {

    public static final String usernameKey = "usernameKey";
    public static final String iconKey = "iconKey";

    private Button GoogleMapsButton;
    private TextView usernameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                getString(usernameKey, "defaultStringIfNothingFound");

        usernameView = (TextView)findViewById(R.id.username);
        usernameView.setText(username);

        // Start up google maps activity when user clicks this button
        GoogleMapsButton = (Button)findViewById(R.id.googleMapsButton);
        GoogleMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
