package cs4720.cs4720finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ProfileActivity extends AppCompatActivity {

    private Set<String> allItems;
    private BaseAdapter adapter;
    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        grid = (GridView) findViewById(R.id.gridView);

        SharedPreferences sharedPref = getSharedPreferences("items", Context.MODE_PRIVATE);
        allItems = sharedPref.getStringSet("key", null);
        if(allItems != null) {
            ArrayList<String> itemsAsList = new ArrayList<String>(allItems);
            Log.d("Items", "" + itemsAsList);
            grid.setAdapter(new ImageAdapter(this, itemsAsList));
        }
    }

    public class ImageAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<String> items;

        public ImageAdapter(Context context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;
            ImageView imageView = new ImageView(context);
            if (convertView == null) {

                gridView = new View(context);



                String item = items.get(position);

                Log.d("Selected item", item);

                Resources res = getResources();
                int id = res.getIdentifier(item, "mipmap", ProfileActivity.this.getPackageName());
                Log.d("ID", "" + id);
                imageView.setImageResource(id);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            } else {
                gridView = (View) convertView;
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
}
