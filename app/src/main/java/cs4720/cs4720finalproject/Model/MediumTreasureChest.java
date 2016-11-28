package cs4720.cs4720finalproject.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/20/2016.
 */
public class MediumTreasureChest implements TreasureChest {

    private final transient LatLng latLng;
    private ArrayList<String> itemList = new ArrayList<String>();
    private static final long serialVersionUID = 25678912531645L;

    public MediumTreasureChest(LatLng latLng) {
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

