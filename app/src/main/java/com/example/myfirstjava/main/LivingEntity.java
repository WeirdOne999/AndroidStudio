package com.example.myfirstjava.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;
import com.example.myfirstjava.mgp2d.core.GameEntity;
import com.example.myfirstjava.mgp2d.core.GameScene;

import java.util.Random;

public class LivingEntity extends PhysicsEntity {

    public enum Item {
        SWORD, // more damage
        AXE, // mine wood
        PICKAXE, // mine stone
        NONE
    }

    /*
    SWORD, // more damage
        AXE, // mine wood
        PICKAXE, // mine stone

        WOODEN,
        STONE,
        IRON,
        GOLD,
        DIAMOND,
     */

    public enum Level{
        WOODEN,
        STONE,
        IRON,
        GOLD,
        DIAMOND,
        NONE
    }

    public int[] itemDrawable = {R.drawable.woodenaxe,R.drawable.stoneaxe,R.drawable.ironaxe,R.drawable.goldaxe,R.drawable.diamondaxe,
                                 R.drawable.woodenpickaxe,R.drawable.stonepickaxe,R.drawable.ironpickaxe,R.drawable.goldpickaxe,R.drawable.diamondpickaxe,
                                 R.drawable.woodensword,R.drawable.stonesword,R.drawable.ironsword,R.drawable.goldsword,R.drawable.diamondsword};

    String[] AxeResource = {"Wood","BirchWood","OakWood","PaleWood","CrimsonWood"};
    String[] PickaxeResource = {"Stone","Iron","Gold","Diamond"};
    Item heldItem = Item.NONE;
    Level heldItemLevel = Level.WOODEN;
    Bitmap _itemsprite;

    private float Health = 0;
    public Holder onHolder;
    public void SetHealth(float health){
        if (health < Health){
            lostHealth();
        }
        Health = health;
        Log.d("HEALTHENEM", "" + health);
    }

    public void lostHealth(){

    }

    public float getHealth(){return Health;}
    public boolean isEnemy;
    @Override
    public void onUpdate(float dt, GameScene gamescene) {
        super.onUpdate(dt, gamescene);
        //Log.d("CHIKCENTEST","LIVINGENTITY");
        if (Health <= 0){
            if (!isEnemy){
                onHolder._mob = null;
                MainGameScene.instance.plantmob[getLayer()]--;
            }
            else {
                MainGameScene.instance.enemymobs[getLayer()]--;
                MainGameScene.instance.Score++;
            }
            destroy();
        }
    }

    public void SetItem(Item newItem, Level newLevel){
        heldItem = newItem;
        heldItemLevel = newLevel;

        int index = 0;
        if(heldItem != Item.NONE){
            if (heldItem == Item.AXE){
                index = 0;
            }
            else if (heldItem == Item.PICKAXE){
                index = 5;
            }
            else if (heldItem == Item.SWORD){
                index = 10;
            }

            if (heldItemLevel == Level.WOODEN){
                index += 0;
            }
            else if (heldItemLevel == Level.STONE){
                index += 1;
            }
            else if (heldItemLevel == Level.IRON){
                index += 2;
            }
            else if (heldItemLevel == Level.GOLD){
                index += 3;
            }
            else if (heldItemLevel == Level.DIAMOND){
                index += 4;
            }

            Bitmap bmp = BitmapFactory.decodeResource(GameActivity.instance.getResources(), itemDrawable[index]);
            int itemScale = (int)getScreenHeight() / 15;
            _itemsprite = Bitmap.createScaledBitmap(bmp,itemScale,itemScale,true);

        }

    }

    public int UseItem(GameScene gamescene){
        if (heldItem == Item.NONE){
            return 1;
        }

        int amountReturn = 0;
        if (heldItemLevel == Level.WOODEN){
            amountReturn = 2;
        } else if (heldItemLevel == Level.STONE){
            amountReturn = 3;
        }
        else if (heldItemLevel == Level.IRON){
            amountReturn = 4;
        }
        else if (heldItemLevel == Level.GOLD){
            amountReturn = 5;
        }
        else if (heldItemLevel == Level.DIAMOND){
            amountReturn = 6;
        }



        /*
        String[] AxeResource = {"Wood","Birch Wood","Oak Wood","Pale Wood","Crimson Wood"};
    String[] PickaxeResource = {"Stone","Iron","Gold","Diamond"};
         */

        if (heldItem == Item.SWORD){
            return amountReturn;
        } else if (heldItem == Item.AXE){
            int randomNumber = new Random().nextInt(amountReturn) + 1;
            int maxAmt = Math.abs(randomNumber - amountReturn - 1);
            Log.d("ITEMMINECRAFT",randomNumber + " " + amountReturn + " " + maxAmt);
            gamescene.addVariable(AxeResource[randomNumber-1],new Random().nextInt(maxAmt)+1);


            /*
            Wooden - > gets 2 & below [1-2,2-1]
            Stone - > gets 3 & below [1-3,2-2,3-1]
            Iron - > gets 4 & below [1-4,2-3,3-2,4-1]
            Gold - > gets 5 & below [1-5,2-4,3-3,4-2,5-1]
            Diamond - > gets 5 & below [1-6,2-5,3-4,4-3,5-2]
             */
        } else if (heldItem == Item.PICKAXE){
            int randomNumber = new Random().nextInt(amountReturn - 1) + 1;
            int maxAmt = Math.abs(randomNumber - amountReturn - 1 - 1);
            gamescene.addVariable(PickaxeResource[randomNumber-1],new Random().nextInt(maxAmt)+1);
            /*
            Wooden - > gets 1 & below [1-1]
            Stone - > gets 2 & below [1-2,2-1]
            Iron - > gets 3 & below [1-3,2-2,3-1]
            Gold - > gets 4 & below [1-4,2-3,3-2,4-1]
            Diamond - > gets 4 & below [1-5,2-4,3-3,4-2]
             */
        }

        return 1;


    }



    @Override
    public void onRender(Canvas canvas) {
        super.onRender(canvas);

        if(heldItem != Item.NONE){
            canvas.drawBitmap(_itemsprite, _position.x - (float) _itemsprite.getWidth() / 2, _position.y - (float)
                    _itemsprite.getHeight() / 2 , null);
        }


    }
}
