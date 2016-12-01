package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static cs4720.cs4720finalproject.R.id.imageButton;

public class HomePageActivity extends AppCompatActivity implements SensorEventListener {

    public static final String usernameKey = "usernameKey";
    public static final String iconKey = "iconKey";

    private Button GoogleMapsButton;
    private TextView usernameView;
    private TextView direction;
    private ImageButton icon;
    private GridView grid;

    // record the compass picture angle turned
    private float currentDegree = 0f;

            // device sensor manager
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float azimut;
    float[] mGravity;
    float[] mGeomagnetic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Pull the username and icon from local storage
        String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                getString(usernameKey, "defaultStringIfNothingFound");

        String iconNum = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                getString(iconKey, "defaultStringIfNothingFound");

        // Set up the icon image that the user selected
        icon = (ImageButton)findViewById(R.id.icon);

        if (iconNum.equals("1")) {
            icon.setImageResource(R.drawable.profile_icon1);
        } else if (iconNum.equals("3")) {
            icon.setImageResource(R.drawable.profile_icon2);
        } else if (iconNum.equals("2")) {
            icon.setImageResource(R.drawable.profile_icon3);
        }

        //Log.d("Owen hello", iconNum);

        usernameView = (TextView)findViewById(R.id.username);
        usernameView.setText(username);

        direction = (TextView)findViewById(R.id.orientation);


        // Start up google maps activity when user clicks this button
        GoogleMapsButton = (Button)findViewById(R.id.googleMapsButton);
        GoogleMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        // Set up the sensors
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //Populate gridview with items
        grid = (GridView) findViewById(R.id.gridView2);

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        ArrayList<String> allItems = tinyDB.getListString("allItems");
        if(allItems != null) {
            Log.d("Items", "" + allItems);
            Collections.sort(allItems);
            grid.setAdapter(new ImageAdapter(this, allItems, grid));
        }
    }

    // Use the value from the accelerometer to set the direction. 8 cardinal directions. maybe change to 4
    public void setDirectionText() {
//        if (currentDegree <= 30 && currentDegree >= -30){
//            direction.setText("North");
//        } else if (currentDegree > 30 && currentDegree < 60) {
//            direction.setText("NorthWest");
//        } else if (currentDegree >= 60 && currentDegree <= 120) {
//            direction.setText("West");
//        } else if (currentDegree > 120 && currentDegree < 160) {
//            direction.setText("SouthWest");
//        } else if (currentDegree >= 160 && currentDegree >= -180) {
//            direction.setText("South");
//        } else if (currentDegree < -120 && currentDegree > -180) {
//            direction.setText("SouthEast");
//        } else if (currentDegree <= -60 && currentDegree <= -120) {
//            direction.setText("East");
//        } else if (currentDegree < -30 && currentDegree > -60) {
//            direction.setText("NorthEast");
//        }

        if (currentDegree <= 60 && currentDegree >=-60) {
            direction.setText("North");
        } else if (currentDegree >= 120 && currentDegree >= -120) {
            direction.setText("South");
        } else if (currentDegree > 60 && currentDegree < 120) {
            direction.setText("West");
        } else if (currentDegree < -60 && currentDegree > -120) {
            direction.setText("East");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            //Log.d("hello","hello");
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll

                //Log.d("Owen sup", Float.toString(azimut));
                currentDegree = -azimut * 360 / (2 * 3.14159f);
                setDirectionText();
                //Log.d("rotate", Float.toString(rotation));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class ImageAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> items;
        private GridView grid;

        public ImageAdapter(Context context, ArrayList<String> items, GridView grid) {
            this.context = context;
            this.items = items;
            this.grid = grid;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //ImageView imageView = new ImageView(context);
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
                imageView.setAdjustViewBounds(false);
            } else {
                imageView = (ImageView) convertView;
            }
            if(imageView != null) {
                String item = items.get(position);
                Resources res = getResources();
                int id = res.getIdentifier(item, "mipmap", HomePageActivity.this.getPackageName());
                imageView.setImageResource(id);
            }
            return imageView;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
    }
}
