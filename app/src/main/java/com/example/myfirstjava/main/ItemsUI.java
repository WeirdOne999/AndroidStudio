package com.example.myfirstjava.main;

public class ItemsUI {
    private int imageResource; // Drawable resource ID
    private String name;
    private int amount;

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
