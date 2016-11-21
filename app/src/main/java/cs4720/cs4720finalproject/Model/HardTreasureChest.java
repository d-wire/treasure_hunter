package cs4720.cs4720finalproject.Model;

import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/20/2016.
 */
public class HardTreasureChest implements TreasureChest {
    private final float longitude;
    private final float latitude;
    private ArrayList<Item> itemList;

    public HardTreasureChest(float longitude, float latitude, ArrayList<Item> itemList) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.itemList = itemList;
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


