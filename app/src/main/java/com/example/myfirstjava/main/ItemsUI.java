package com.example.myfirstjava.main;

import java.util.HashMap;
import java.util.List;

public class ItemsUI {
    private int imageResource; // Drawable resource ID
    private String name;
    private int amount;

    public HashMap<String, Integer> materials = new HashMap<>();

    //private List<String> requiredmaterial;

    // Constructor
    public ItemsUI(int imageResource, String name, int amount) {
        this.imageResource = imageResource;
        this.name = name;
        this.amount = amount;
    }

    // Getters
    public int getImageResource() {
        return imageResource;
    }





    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    // Setters (optional)
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
