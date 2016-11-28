package cs4720.cs4720finalproject.Model;

/**
 * Created by liamj_000 on 11/27/2016.
 */
public class IronHelmet implements Item {
    private static final String file = "iron_helmet_item";
    private String rarity;

    public IronHelmet() {
        this.rarity = "rare";
    }

    public String getFileName() {
        return file;
    }
}
