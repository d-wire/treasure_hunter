package cs4720.cs4720finalproject.Model;

/**
 * Created by liamj_000 on 11/27/2016.
 */
public class Excalibur implements Item {
    private static final String file = "excalibur_item";
    private static final long serialVersionUID = 1315846512551L;
    private String rarity;

    public Excalibur() {
        this.rarity = "very rare";
    }

    public String getFileName() {
        return file;
    }
}
