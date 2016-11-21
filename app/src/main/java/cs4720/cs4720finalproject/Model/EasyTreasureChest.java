package cs4720.cs4720finalproject.Model;

import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/20/2016.
 */
public class EasyTreasureChest implements TreasureChest {
    private final float longitude;
    private final float latitude;
    private ArrayList<Item> itemList;
    private String difficulty;

    public EasyTreasureChest(float longitude, float latitude, ArrayList<Item> itemList, String difficulty) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.itemList = itemList;
        this.difficulty = difficulty;
    }


    public String getDifficulty() {
        return difficulty;
    }

    public boolean addItem(Item item) {
        if(!this.itemList.contains(item)) {
            this.itemList.add(item);
            return true;
        }
        return false;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
