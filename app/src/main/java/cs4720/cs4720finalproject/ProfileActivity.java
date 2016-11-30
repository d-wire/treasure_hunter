package cs4720.cs4720finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Image;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
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

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        ArrayList<String> allItems = tinyDB.getListString("allItems");
        if(allItems != null) {
            Log.d("Items", "" + allItems);
            grid.setAdapter(new ImageAdapter(this, allItems, grid));
        }
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView imageView = new ImageView(context);
            if (convertView == null) {
                String item = items.get(position);

                Log.d("Selected item", item);

                Resources res = getResources();
                int id = res.getIdentifier(item, "mipmap", ProfileActivity.this.getPackageName());
                Log.d("ID", "" + id);
                imageView.setImageResource(id);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            } else {
                imageView = (ImageView) convertView;
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
