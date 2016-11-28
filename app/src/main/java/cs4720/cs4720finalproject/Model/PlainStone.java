package cs4720.cs4720finalproject.Model;

/**
 * Created by liamj_000 on 11/27/2016.
 */
public class PlainStone implements Item {
    private static final String file = "plain_stone_item";
    private static final long serialVersionUID = 3361598271352L;
    private String rarity;

    public PlainStone() {
        this.rarity = "common";
    }

    public String getFileName() {
        return file;
    }
}
