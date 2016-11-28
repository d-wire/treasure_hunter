package cs4720.cs4720finalproject.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/20/2016.
 */
public interface TreasureChest extends Serializable {
    public LatLng getLatLng();
    public ArrayList<String> getItemList();
}
