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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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






        // Create a row of buttons










        hatcheggwidth = 100; // Width in dp
        hatcheggheight = 100; // Height in dp
        hatchbuttonStartX = 500; // Starting X position
        hatchbuttonStartY = screenHeight - 900; // Fixed Y position near the bottom of the screen

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
                    Thread.sleep(10); // Avoid busy-waiting if the surface isn't ready
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

            // Calculate position for each button in the row
            int buttonPosX = hatchbuttonStartX + (i * (hatcheggwidth + 300)); // 20 is spacing between buttons

            FrameLayout.LayoutParams hatchbuttonparams = new FrameLayout.LayoutParams(
                    (int) (hatcheggwidth * getResources().getDisplayMetrics().density),
                    (int) (hatcheggheight * getResources().getDisplayMetrics().density)
            );
            hatchbuttonparams.leftMargin = buttonPosX; // X position
            hatchbuttonparams.topMargin = hatchbuttonStartY; // Fixed Y position

            hatchingeggs[i].setLayoutParams(hatchbuttonparams);

            // Add the button to the FrameLayout
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
        tohouseparams.leftMargin = screenWidth - 500; // X position
        tohouseparams.topMargin = screenHeight - 200; // Fixed Y position

        toFarmButton.setLayoutParams(tohouseparams);

        toCraftButton = new Button(this.getContext());
        toCraftButton.setText("CRAFT");
        toCraftButton.setBackgroundResource(R.drawable.button);
        toCraftButton.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams tocraftparams = new FrameLayout.LayoutParams(
                (int) (150 * getResources().getDisplayMetrics().density),
                (int) (50 * getResources().getDisplayMetrics().density)
        );
        tocraftparams.leftMargin = screenWidth - 500; // X position
        tocraftparams.topMargin = screenHeight - 400; // Fixed Y position

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
            imageParams.leftMargin = screenWidth / 2+ 400; // Center it
            imageParams.topMargin = screenHeight / 2 -350; // Adjust position
            //craftedItemImage.setBackgroundResource(R.drawable.irongolem);

            craftedItemImage.setLayoutParams(imageParams);
            craftedItemImage.setVisibility(View.VISIBLE); // Hide initially
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
            textParams.topMargin = screenHeight -350; // Position below image

            craftedItemText.setLayoutParams(textParams);
            frameLayout.addView(craftedItemText);
        }

        if (confirmcraftbutton == null ) {
            confirmcraftbutton = new Button(getContext());

            confirmcraftbutton.setText("CRAFT");
            confirmcraftbutton.setBackgroundResource(R.drawable.button);
            confirmcraftbutton.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    (int) (120 * getResources().getDisplayMetrics().density),
                    (int) (50 * getResources().getDisplayMetrics().density)
            );
            buttonParams.leftMargin = screenWidth / 2 + 350;
            buttonParams.topMargin = screenHeight -200; // Position below image
            confirmcraftbutton.setLayoutParams(buttonParams);

            frameLayout.addView(confirmcraftbutton);



        }
        craftedItemText.bringToFront();

        // Remove any existing ScrollView if it exists
        if (craftingScrollView != null) {
            frameLayout.removeView(craftingScrollView);
            craftingScrollView = null; // Clear reference
        }

        // Create a new ScrollView
        craftingScrollView = new ScrollView(getContext());
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        craftingScrollView.setLayoutParams(scrollParams);

        // Create a LinearLayout inside the ScrollView
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add buttons dynamically
        for (int i = 0; i < myItems.size(); i++) {
            Button craftButton = new Button(getContext());

            craftButton.setText(myItems.get(i).getName());
            craftButton.setBackgroundResource(R.drawable.button);
            craftButton.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (50 * getResources().getDisplayMetrics().density)
            );
            buttonParams.setMargins(20, 5, 1200, 5);
            craftButton.setLayoutParams(buttonParams);

            linearLayout.addView(craftButton);
            UIitemButtons.add(craftButton);


        }

        // Add the LinearLayout inside the ScrollView
        craftingScrollView.addView(linearLayout);

        // Add ScrollView to the main FrameLayout
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
//        for (int i = 0; i < eggButtons.size(); i++){
//            eggButtons.get(i).setVisibility(View.INVISIBLE);
//        }
        itemsscrollview.setVisibility(View.INVISIBLE);
        toggleinvenbutton.setVisibility(View.INVISIBLE);
        toCraftButton.setVisibility(View.INVISIBLE);
        toHouseButton.setVisibility(View.INVISIBLE);
        toFarmButton.setVisibility(View.VISIBLE);

    }

    public void GameLayout(){
        for (Button buttons : characterButtons){
            buttons.setVisibility(View.VISIBLE);

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

        // Create a new ScrollView
        itemsscrollview = new ScrollView(getContext());
        FrameLayout.LayoutParams scrollParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );

        scrollParams.setMargins(0, 130, 0, 0);
        itemsscrollview.setLayoutParams(scrollParams);
        itemsscrollview.setVisibility(View.INVISIBLE);
// Create a LinearLayout inside the ScrollView
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

// Add buttons dynamically
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

// Add LinearLayout to the ScrollView
        itemsscrollview.addView(linearLayout);

// Add ScrollView to the parent FrameLayout
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
    private void update(float dt) {

        if (characterButtons != null && characterButtons.length > 0) {
            // Modify text for each button dynamically in the update loop
            post(() -> {
                for (int i = 0; i < characterButtons.length; i++) {
                    //characterButtons[i].setText("10");
                    //Log.d("Update", "Button " + (i + 1) + " text updated");
                }
            });
        }


        // Use post() to update the button's position on the UI thread



    }



    private void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK); // Optional: Clear with black color before drawing

        // Draw the background image
        if (backgroundBitmap != null) {
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
        }


    }

    public void resume() {
        if (isRunning) {
            // If already running, don't start another thread
            return;
        }

        isRunning = true;

        // Start a new thread safely
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
                    gameThread.join(); // Wait for the thread to terminate
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    gameThread = null; // Ensure the thread reference is cleared
                }
            }
        }
    }

}
