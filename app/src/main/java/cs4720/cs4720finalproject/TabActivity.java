package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends AppCompatActivity {

    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        tabHost = (TabHost) findViewById(R.id.tabHost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        //TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        tab1.setIndicator("Map");
        tab1.setContent(new Intent(this, MapsActivity.class));

        //tab2.setIndicator("Profile");
        //tab2.setContent(new Intent(this, ProfileActivity.class));

        tabHost.addTab(tab1);
        //tabHost.addTab(tab2);
    }
}
