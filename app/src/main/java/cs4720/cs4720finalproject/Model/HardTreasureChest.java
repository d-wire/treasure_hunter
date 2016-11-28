package cs4720.cs4720finalproject.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/20/2016.
 */
public class HardTreasureChest implements TreasureChest {
    private final transient LatLng latLng;
    private ArrayList<String> itemList = new ArrayList<String>();
    private static final long serialVersionUID = 315275964648595L;

    public HardTreasureChest(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean addItem(String item) {
        this.itemList.add(item);
        return true;
    }

    public ArrayList<String> getItemList() {
        return itemList;
    }


    public LatLng getLatLng() {
        return latLng;
    }
}


