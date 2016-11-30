package cs4720.cs4720finalproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cs4720.cs4720finalproject.Model.EasyTreasureChest;
import cs4720.cs4720finalproject.Model.Item;
import cs4720.cs4720finalproject.Model.TreasureChest;

public class QuizCompleteActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private TextView textView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_complete);
        textView = (TextView) findViewById(R.id.textView9);
        imageView1 = (ImageView) findViewById(R.id.imageView);
        imageViews.add(imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageViews.add(imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageViews.add(imageView3);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("sent chest");
        TreasureChest recievedChest = (TreasureChest) bundle.getSerializable("chest");
        items = recievedChest.getItemList();
        Resources res = getResources();
        for(int i = 0; i < items.size(); i++) {
            int id = res.getIdentifier(items.get(i), "mipmap", QuizCompleteActivity.this.getPackageName());
            Log.d("Correct ID", "" + id);
            imageViews.get(i).setBackgroundResource(id);
        }

    }

    public void finish(View v) {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
    }
}
