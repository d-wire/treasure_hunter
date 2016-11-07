package cs4720.cs4720finalproject.Model;

import java.util.ArrayList;

/**
 * Created by liamj_000 on 11/7/2016.
 */
public class User {
    private String username;
    private ArrayList<User> friendsList;
    private ArrayList<Item> lootList;

    public User(String username) {
        this.username = username;
        this.friendsList = new ArrayList<User>();
        this.lootList = new ArrayList<Item>();
    }

    public String getUsername() {
        return this.username;
    }

    public boolean addFriend(User friend) {
        if(!this.friendsList.contains(friend)) {
            this.friendsList.add(friend);
            return true;
        }
        else {
            return false;
        }

    }
}
