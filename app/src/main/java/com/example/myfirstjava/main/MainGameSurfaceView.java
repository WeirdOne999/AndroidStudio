package com.example.myfirstjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.GridLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//SURFACE VIEW CLASS
public class MainGameSurfaceView extends SurfaceView implements Runnable {
    private Thread gameThread;
    public static MainGameSurfaceView instance;
    private boolean isRunning;
    private SurfaceHolder surfaceHolder;

    private Bitmap backgroundBitmap;
    private int backgroundWidth, backgroundHeight;

    public Button[] characterButtons = new Button[4];
    public Drawable[] characterdrawables = new Drawable[4];
    public String[] characterNames = {"Chicken", "Llama", "Iron Golem", "Sheep"};
    public int[] cost = {10, 15, 20, 20};
    public long[] hatchingduration = {5000, 7000, 10000, 10000};

    public int[] characteramounts = new int[4];
    public int characterbuttonindex =0;
    public Button[] hatchingeggs = new Button[4];
    public Drawable[] hatchingeggsdrawables = new Drawable[4];
    public Button[] eggshop = new Button[5];
    public Button toHouseButton;
    public Button toCraftButton;
    public Button toFarmButton;
    public List<Button> eggButtons = new ArrayList<>();
    public List<EggClass> eggs = new ArrayList<EggClass>();
    private int screenWidth, screenHeight;

    private int buttonWidth, buttonHeight;

    public int buttonposX, buttonpoxY;
    public int buttonStartX, buttonStartY;

    public int hatcheggwidth, hatcheggheight;
    public int hatchbuttonStartX, hatchbuttonStartY;

    private ScrollView craftingScrollView; // Store ScrollView reference
    public ImageView craftedItemImage;

    public TextView craftedItemText;

    public Button confirmcraftbutton;

    public ScrollView itemsscrollview;
    public List<Button> itembuttons;
    public List<Button> UIitemButtons;
    public Button toggleinvenbutton;
    public boolean mobinvenactive;

    public ScrollView MaterialScrollview;
    public List<TextView> MaterialAmount;
    public TextView eggCountText;
    LinearLayout eggLayout;
    public List<ItemsUI> myItems = ItemInventory.getItems();
    public void setSize(int width,int height){
        screenWidth = width;
        screenHeight = height;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public MainGameSurfaceView(Context context, FrameLayout frameLayout) {
        super(context);
        itembuttons = new ArrayList<>();
        UIitemButtons = new ArrayList<>();
        MaterialAmount = new ArrayList<>();

        AudioClass.getInstance().PlayBackgroundMusic(context, R.raw.ariamath, true);
        instance = this;
        setZOrderOnTop(true);
        ImageView backgroundView = new ImageView(context);
        backgroundView.setImageResource(R.drawable.blank_bg); // Replace 'blank_bg' with your image
        FrameLayout.LayoutParams backgroundParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        backgroundView.setLayoutParams(backgroundParams);
        frameLayout.addView(backgroundView); // Add the background first

        surfaceHolder = getHolder();
        setZOrderOnTop(false); // Keep SurfaceView below other views
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        buttonWidth = 50; // Width in dp
        buttonHeight = 50; // Height in dp
        buttonStartX = 0 + screenWidth / 13; // Starting X position
        buttonStartY = screenHeight - 900; // Fixed Y position near the bottom of the screen

        characterdrawables[0] = getResources().getDrawable(R.drawable.chicken, null); // Replace with actual drawable
        characterdrawables[1] = getResources().getDrawable(R.drawable.llama, null);
        characterdrawables[2] = getResources().getDrawable(R.drawable.irongolem, null);
        characterdrawables[3] = getResources().getDrawable(R.drawable.sheep, null);

        hatchingeggsdrawables[0] = getResources().getDrawable(R.drawable.chickenegg, null);
        hatchingeggsdrawables[1] = getResources().getDrawable(R.drawable.llamaegg, null);
        hatchingeggsdrawables[2] = getResources().getDrawable(R.drawable.irongolemegg, null);
        hatchingeggsdrawables[3] = getResources().getDrawable(R.drawable.sheepegg, null);


        Arrays.fill(characteramounts, 10);

















        hatcheggwidth = 100;
        hatcheggheight = 100;
        hatchbuttonStartX = 500;
        hatchbuttonStartY = screenHeight - 900;

        // Create a row of buttons

        mobinvenactive = true;


    }


    public Button[] getCharacterButton() {
        return characterButtons;
    }

    @Override
    public void run() {
        while (isRunning) {

            if (!surfaceHolder.getSurface().isValid()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            float dt = 1.0f / 60.0f; // Assuming 60 FPS for simplicity
            update(dt);
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                render(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }


        }

        Log.d ("HATCHINGBUTTONS", String.valueOf(hatchingeggs.length));
    }

    public void createhatchingbuttons(){
        FrameLayout frameLayout = (FrameLayout) this.getParent();
        for (int i = 0; i < hatchingeggs.length; i++) {
            hatchingeggs[i] = new Button(this.getContext());
            hatchingeggs[i].setBackground(hatchingeggsdrawables[i]);

            hatchingeggs[i].setText("" + cost[i]);
            hatchingeggs[i].setTextColor(Color.WHITE);
            hatchingeggs[i].setShadowLayer(5, 2, 2, Color.BLACK);
            hatchingeggs[i].setGravity(Gravity.RIGHT | Gravity.BOTTOM);


            int buttonPosX = hatchbuttonStartX + (i * (hatcheggwidth + 300));

            FrameLayout.LayoutParams hatchbuttonparams = new FrameLayout.LayoutParams(
                    (int) (hatcheggwidth * getResources().getDisplayMetrics().density),
                    (int) (hatcheggheight * getResources().getDisplayMetrics().density)
            );
            hatchbuttonparams.leftMargin = buttonPosX; // X position
            hatchbuttonparams.topMargin = hatchbuttonStartY; // Fixed Y position

            hatchingeggs[i].setLayoutParams(hatchbuttonparams);


            frameLayout.addView(hatchingeggs[i]);
        }
        toFarmButton = new Button(this.getContext());
        toFarmButton.setText("FARM");
        toFarmButton.setBackgroundResource(R.drawable.button);
        toFarmButton.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams tohouseparams = new FrameLayout.LayoutParams(
                (int) (150 * getResources().getDisplayMetrics().density),
                (int) (50 * getResources().getDisplayMetrics().density)
        );
        tohouseparams.leftMargin = screenWidth - 500;
        tohouseparams.topMargin = screenHeight - 200;

        toFarmButton.setLayoutParams(tohouseparams);

        toCraftButton = new Button(this.getContext());
        toCraftButton.setText("CRAFT");
        toCraftButton.setBackgroundResource(R.drawable.button);
        toCraftButton.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams tocraftparams = new FrameLayout.LayoutParams(
                (int) (150 * getResources().getDisplayMetrics().density),
                (int) (50 * getResources().getDisplayMetrics().density)
        );
        tocraftparams.leftMargin = screenWidth - 500;
        tocraftparams.topMargin = screenHeight - 400;

        toCraftButton.setLayoutParams(tocraftparams);
        frameLayout.addView(toCraftButton);
        frameLayout.addView(toFarmButton);


    }

    public void createCraftingLayout() {
        FrameLayout frameLayout = (FrameLayout) this.getParent();

        if (craftedItemImage == null) {
            craftedItemImage = new ImageView(getContext());
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(
                    (int) (150 * getResources().getDisplayMetrics().density),
                    (int) (150 * getResources().getDisplayMetrics().density)
            );
            imageParams.leftMargin = screenWidth / 2 + 400;
            imageParams.topMargin = screenHeight / 2 - 350;
            craftedItemImage.setLayoutParams(imageParams);
            craftedItemImage.setVisibility(View.VISIBLE);
            frameLayout.addView(craftedItemImage);
        }

        if (craftedItemText == null) {
            craftedItemText = new TextView(getContext());
            craftedItemText.setTextSize(30);
            craftedItemText.setText("Crafted Item");
            craftedItemText.setTextColor(Color.WHITE);
            craftedItemText.setGravity(Gravity.CENTER);
            craftedItemText.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            craftedItemText.setVisibility(View.VISIBLE);

            FrameLayout.LayoutParams textParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            textParams.leftMargin = screenWidth / 2 + 350;
            textParams.topMargin = screenHeight - 350; // Position below image
            craftedItemText.setLayoutParams(textParams);
            frameLayout.addView(craftedItemText);
        }

        if (confirmcraftbutton == null) {
            confirmcraftbutton = new Button(getContext());
            confirmcraftbutton.setText("CRAFT");
            confirmcraftbutton.setBackgroundResource(R.drawable.button);
            confirmcraftbutton.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    (int) (120 * getResources().getDisplayMetrics().density),
                    (int) (50 * getResources().getDisplayMetrics().density)
            );
            buttonParams.leftMargin = screenWidth / 2 + 350;
            buttonParams.topMargin = screenHeight - 200; // Position below image
            confirmcraftbutton.setLayoutParams(buttonParams);
            frameLayout.addView(confirmcraftbutton);
        }

        craftedItemText.bringToFront();


        if (craftingScrollView != null) {
            frameLayout.removeView(craftingScrollView);
            craftingScrollView = null; // Clear reference
        }


        craftingScrollView = new ScrollView(getContext());
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        craftingScrollView.setLayoutParams(scrollParams);


        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setColumnCount(4);
        gridLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));


        for (int i = 0; i < myItems.size(); i++) {
            Button craftButton = new Button(getContext());
            //craftButton.setText(myItems.get(i).getName());

            craftButton.setBackgroundResource(myItems.get(i).getImageResource());
            craftButton.setTextColor(Color.WHITE);


            GridLayout.LayoutParams buttonParams = new GridLayout.LayoutParams();
            buttonParams.width = (int) (100 * getResources().getDisplayMetrics().density);
            buttonParams.height = (int) (100 * getResources().getDisplayMetrics().density);
            buttonParams.setMargins(10, 10, 10, 10);

            craftButton.setLayoutParams(buttonParams);
            gridLayout.addView(craftButton);
            UIitemButtons.add(craftButton);
        }


        craftingScrollView.addView(gridLayout);


        frameLayout.addView(craftingScrollView);
    }

    public void removeCraftingScrollView() {


        craftingScrollView.setVisibility(View.INVISIBLE);
        craftedItemImage.setVisibility(View.INVISIBLE);
        craftedItemText.setVisibility(View.INVISIBLE);
        confirmcraftbutton.setVisibility(View.INVISIBLE);
    }

    public void addCraftingScrollView() {


        craftingScrollView.setVisibility(View.VISIBLE);
        craftedItemImage.setVisibility(View.VISIBLE);
        craftedItemText.setVisibility(View.VISIBLE);
        confirmcraftbutton.setVisibility(View.VISIBLE);
    }


    public void HatchingLayout(){
        for (Button buttons : characterButtons){
            buttons.setVisibility(View.INVISIBLE);

        }


        for (Button buttons : hatchingeggs){
            buttons.setVisibility(View.VISIBLE);

        }

        removeCraftingScrollView();

        for (int i = 0; i < eggButtons.size(); i++){
            eggButtons.get(i).setVisibility(View.VISIBLE);
        }
        if (MaterialScrollview != null){
            MaterialScrollview.setVisibility(View.INVISIBLE);
        }
        if (eggLayout != null){
            eggLayout.setVisibility(View.INVISIBLE);
        }

        itemsscrollview.setVisibility(View.INVISIBLE);
        toggleinvenbutton.setVisibility(View.INVISIBLE);
        toCraftButton.setVisibility(View.VISIBLE);
        toHouseButton.setVisibility(View.INVISIBLE);
        toFarmButton.setVisibility(View.VISIBLE);

    }

    public void CraftingLayout(){
        for (Button buttons : characterButtons){
            buttons.setVisibility(View.INVISIBLE);

        }


        for (Button buttons : hatchingeggs){
            buttons.setVisibility(View.INVISIBLE);

        }


        addCraftingScrollView();
        craftedItemImage.bringToFront();
        craftedItemText.bringToFront();
        confirmcraftbutton.bringToFront();
        craftingScrollView.bringToFront();

        if (MaterialScrollview != null){
            MaterialScrollview.setVisibility(View.INVISIBLE);
        }
        if (eggLayout != null){
            eggLayout.setVisibility(View.INVISIBLE);
        }
        for (Button buttons : hatchingeggs){
            buttons.setVisibility(View.INVISIBLE);

        }

        itemsscrollview.setVisibility(View.INVISIBLE);
        toggleinvenbutton.setVisibility(View.INVISIBLE);
        toCraftButton.setVisibility(View.INVISIBLE);
        toHouseButton.setVisibility(View.INVISIBLE);
        toFarmButton.setVisibility(View.VISIBLE);
        for (Button buttons : eggButtons){
            buttons.setVisibility(View.INVISIBLE);

        }
    }

    public void GameLayout(){
        for (Button buttons : characterButtons){
            buttons.setVisibility(View.VISIBLE);

        }
        if (MaterialScrollview != null){
            MaterialScrollview.setVisibility(View.VISIBLE);
        }
        if (eggLayout != null){
            eggLayout.setVisibility(View.VISIBLE);
        }
        toggleinvenbutton.setVisibility(View.VISIBLE);
        toFarmButton.setVisibility(View.INVISIBLE);
        toHouseButton.setVisibility(View.VISIBLE);
        toCraftButton.setVisibility(View.INVISIBLE);
        for (Button buttons : hatchingeggs){
            buttons.setVisibility(View.INVISIBLE);

        }

        removeCraftingScrollView();

        for (Button buttons : eggButtons){
            buttons.setVisibility(View.INVISIBLE);

        }

    }
    public void creategamebuttons(){
        FrameLayout frameLayout = (FrameLayout) this.getParent();
        createCraftingLayout();
        addCraftingScrollView();
        toggleinvenbutton = new Button(this.getContext());
        toggleinvenbutton.setText("T");
        toggleinvenbutton.setTextColor(Color.WHITE);
        toggleinvenbutton.setBackground(getResources().getDrawable(R.drawable.button, null));
        FrameLayout.LayoutParams invenparams = new FrameLayout.LayoutParams(
                (int) (buttonWidth/1.5 * getResources().getDisplayMetrics().density),
                (int) (buttonHeight/1.5 * getResources().getDisplayMetrics().density)
        );
        invenparams.leftMargin = buttonStartX -150;
        invenparams.topMargin = buttonStartY;
        toggleinvenbutton.setLayoutParams(invenparams);

        frameLayout.addView(toggleinvenbutton);
        for (int i = 0; i < characterButtons.length; i++) {

                if (characterdrawables[i] == null) continue;

                characterButtons[i] = new Button(this.getContext());
                characterButtons[i].setBackground(characterdrawables[i]);
                characterButtons[i].setText("0");
                characterButtons[i].setTextColor(Color.WHITE);
                characterButtons[i].setShadowLayer(5, 2, 2, Color.BLACK);
                characterButtons[i].setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        (int) (buttonWidth * getResources().getDisplayMetrics().density),
                        (int) (buttonHeight * getResources().getDisplayMetrics().density)
                );
                params.leftMargin = buttonStartX;
                params.topMargin = buttonStartY + (i * (buttonHeight + 100));
                characterButtons[i].setLayoutParams(params);

                frameLayout.addView(characterButtons[i]);



        }


        itemsscrollview = new ScrollView(getContext());
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        scrollParams.setMargins(0, 130, 0, 0);
        itemsscrollview.setLayoutParams(scrollParams);
        itemsscrollview.setVisibility(View.INVISIBLE);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));


        for (int i = 0; i < myItems.size(); i++) {
            Button itembutton = new Button(getContext());

            itembutton.setBackgroundResource(myItems.get(i).getImageResource());

            itembutton.setText("0");
            itembutton.setTextColor(Color.WHITE);
            itembutton.setShadowLayer(5, 2, 2, Color.BLACK);
            itembutton.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    (int) (buttonWidth * getResources().getDisplayMetrics().density),
                    (int) (buttonHeight * getResources().getDisplayMetrics().density)
            );
            buttonParams.setMargins(200, 5, 0, 5);
            itembutton.setLayoutParams(buttonParams);
            itembutton.bringToFront();
            linearLayout.addView(itembutton);
            itembuttons.add(itembutton);


            Log.d("DEBUG", "Buttons created: " + itembuttons.size());

        }


        itemsscrollview.addView(linearLayout);

        frameLayout.addView(itemsscrollview);

        toHouseButton = new Button(this.getContext());
        toHouseButton.setText("HOUSE");
        toHouseButton.setBackgroundResource(R.drawable.button);
        toHouseButton.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams tohouseparams = new FrameLayout.LayoutParams(
                (int) (150 * getResources().getDisplayMetrics().density),
                (int) (50 * getResources().getDisplayMetrics().density)
        );
        tohouseparams.leftMargin = screenWidth - 500; // X position
        tohouseparams.topMargin = screenHeight - 200; // Fixed Y position

        toHouseButton.setLayoutParams(tohouseparams);
        frameLayout.addView(toHouseButton);



    }
    public void createMaterialGrid() {
        if (MainGameScene.instance == null || MainGameScene.instance.material == null) {
            Log.e("Game", "MainGameScene.instance or material is null!");
            return;
        }

        (GameActivity.instance).runOnUiThread(() -> {
            FrameLayout frameLayout = (FrameLayout) this.getParent();
            if (frameLayout == null) {
                Log.e("Game", "frameLayout is null!");
                return;
            }

            // Remove existing grid if needed
            if (MaterialScrollview != null) {
                frameLayout.removeView(MaterialScrollview);
                MaterialScrollview = null;
            }

            // Create a new ScrollView
            MaterialScrollview = new ScrollView(getContext());
            FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            scrollParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            scrollParams.bottomMargin = 20; // Offset from the bottom
            MaterialScrollview.setLayoutParams(scrollParams);

            // Container for GridLayout (Ensures Center Alignment)
            LinearLayout containerLayout = new LinearLayout(getContext());
            containerLayout.setOrientation(LinearLayout.VERTICAL);
            containerLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            containerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Create GridLayout
            GridLayout gridLayout = new GridLayout(getContext());
            int totalMaterials = MainGameScene.instance.material.size();
            gridLayout.setRowCount(2); // Always 2 rows
            gridLayout.setColumnCount((int) Math.ceil((double) totalMaterials / 2));

            LinearLayout.LayoutParams gridParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            gridLayout.setLayoutParams(gridParams);

            int row = 0, col = 0;
            for (Map.Entry<String, Integer> entry : MainGameScene.instance.material.entrySet()) {
                String materialName = entry.getKey();
                int materialAmount = entry.getValue();

                LinearLayout itemLayout = new LinearLayout(getContext());
                itemLayout.setOrientation(LinearLayout.HORIZONTAL);
                itemLayout.setGravity(Gravity.CENTER_VERTICAL);
                itemLayout.setPadding(20, 10, 20, 10);

                // ImageView
                ImageView materialImage = new ImageView(getContext());
                materialImage.setImageResource(getMaterialImage(materialName));
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(100, 100);
                materialImage.setLayoutParams(imageParams);

                // TextView
                TextView materialCount = new TextView(getContext());

                materialCount.setText(String.valueOf(materialAmount));
                materialCount.setTextColor(Color.WHITE);
                materialCount.setTextSize(20);
                materialCount.setPadding(20, 0, 0, 0);

                itemLayout.addView(materialImage);
                itemLayout.addView(materialCount);
                MaterialAmount.add(materialCount);

                GridLayout.LayoutParams gridItemParams = new GridLayout.LayoutParams(
                        GridLayout.spec(row), GridLayout.spec(col)
                );
                gridItemParams.setMargins(20, 10, 20, 10);
                itemLayout.setLayoutParams(gridItemParams);

                gridLayout.addView(itemLayout);


                col++;
                if (col >= gridLayout.getColumnCount()) {
                    col = 0;
                    row++;
                }
            }

            containerLayout.addView(gridLayout);
            MaterialScrollview.addView(containerLayout);
            frameLayout.addView(MaterialScrollview);
        });



    }




    public void createEggIcon() {

        if (MainGameScene.instance != null) {

            final int eggAmount = MainGameScene.instance.Egg;


            final FrameLayout frameLayout = (FrameLayout) this.getParent();


            ((GameActivity) getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (eggLayout == null){
                        eggLayout = new LinearLayout(getContext());
                    }

                    eggLayout.setOrientation(LinearLayout.HORIZONTAL);
                    //eggLayout.setGravity(Gravity.CENTER_VERTICAL);
                    eggLayout.setPadding(20, 20, 0, 0);

                    // Create ImageView for egg
                    ImageView eggImage = new ImageView(getContext());
                    eggImage.setImageResource(R.drawable.egg);
                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(100, 100);
                    eggImage.setLayoutParams(imageParams);

                    // Create TextView for egg count
                    if (eggCountText == null){
                        eggCountText = new TextView(getContext());
                    }

                    eggCountText.setText(String.valueOf(eggAmount));
                    eggCountText.setTextColor(Color.WHITE);
                    eggCountText.setTextSize(20);
                    eggCountText.setPadding(20, 0, 0, 0);


                    eggLayout.addView(eggImage);
                    eggLayout.addView(eggCountText);


                    frameLayout.addView(eggLayout);
                }
            });
        }
    }

    private int getMaterialImage(String materialName) {
        switch (materialName) {
            case "Diamond":
                return R.drawable.diamond;
            case "Gold":
                return R.drawable.gold;
            case "Iron":
                return R.drawable.iron;
            case "Stone":
                return R.drawable.stone;
            case "CrimsonWood":
                return R.drawable.crimsonwood;
            case "BirchWood":
                return R.drawable.birchwood;
            case "PaleWood":
                return R.drawable.palewood;
            case "OakWood":
                return R.drawable.oakwood;
            case "Wood":
                return R.drawable.wood;
            default:
                return 0;
        }
    }
    private void update(float dt) {

        if (characterButtons != null && characterButtons.length > 0) {

            post(() -> {
                for (int i = 0; i < characterButtons.length; i++) {
                    //characterButtons[i].setText("10");
                    //Log.d("Update", "Button " + (i + 1) + " text updated");
                }
            });
        }





    }



    private void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        // Draw the background image
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }


    }

    public void resume() {
        if (isRunning) {

            return;
        }

        isRunning = true;


        synchronized (this) {
            if (gameThread == null || !gameThread.isAlive()) {
                gameThread = new Thread(this);
                gameThread.start();
            }
        }
    }

    public void pause() {
        isRunning = false;

        // Safely stop the thread
        synchronized (this) {
            if (gameThread != null) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    gameThread = null;
                }
            }
        }
    }

}
