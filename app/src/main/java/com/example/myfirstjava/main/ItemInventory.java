package com.example.myfirstjava.main;

import com.example.myfirstjava.R;

import java.util.ArrayList;
import java.util.List;

public class ItemInventory {

    public static List<ItemsUI> getItems() {
        List<ItemsUI> itemList = new ArrayList<>();

        itemList.add(new ItemsUI(R.drawable.diamondpickaxe, "Diamond Pickaxe", 5));
        itemList.add(new ItemsUI(R.drawable.diamondaxe, "Diamond Axe", 3));
        itemList.add(new ItemsUI(R.drawable.diamondsword, "Diamond Sword", 10));
        itemList.add(new ItemsUI(R.drawable.goldpickaxe, "Gold Pickaxe", 5));
        itemList.add(new ItemsUI(R.drawable.goldaxe, "Gold Axe", 3));
        itemList.add(new ItemsUI(R.drawable.goldsword, "Gold Sword", 10));
        itemList.add(new ItemsUI(R.drawable.ironpickaxe, "Iron Pickaxe", 5));
        itemList.add(new ItemsUI(R.drawable.ironaxe, "Iron Axe", 3));
        itemList.add(new ItemsUI(R.drawable.ironsword, "Iron Sword", 10));
        itemList.add(new ItemsUI(R.drawable.stonepickaxe, "Stone Pickaxe", 5));
        itemList.add(new ItemsUI(R.drawable.stoneaxe, "Stone Axe", 3));
        itemList.add(new ItemsUI(R.drawable.stonesword, "Stone Sword", 10));
        itemList.add(new ItemsUI(R.drawable.woodenpickaxe, "Wooden Pickaxe", 5));
        itemList.add(new ItemsUI(R.drawable.woodenaxe, "Wooden Axe", 3));
        itemList.add(new ItemsUI(R.drawable.woodensword, "Wooden Sword", 10));

        return itemList;
    }

}
