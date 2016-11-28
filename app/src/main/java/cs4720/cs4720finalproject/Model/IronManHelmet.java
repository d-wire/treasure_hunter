package cs4720.cs4720finalproject.Model;

/**
 * Created by liamj_000 on 11/27/2016.
 */
public class IronManHelmet implements Item {
    private static final String file = "iron_man_helmet_item";
    private String rarity;

    public IronManHelmet() {
        this.rarity = "rare";
    }

    public String getFileName() {
        return file;
    }
}
