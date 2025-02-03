package com.example.myfirstjava.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

public class EndDialog extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing(){return _isShowing; }
    public static void create(){
        _isShowing = false;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.endui, null);

        EditText nameInput = view.findViewById(R.id.player_name_input);
        Button submitButton = view.findViewById(R.id.submit_btn);
        Button buttonNo = view.findViewById(R.id.mainmenu_button);

        submitButton.setOnClickListener(v -> {
            String playerName = nameInput.getText().toString().trim();
            int score = MainGameScene.instance.Score; // Get the current game score

            if (!playerName.isEmpty()) {
                saveScore(playerName, score);
                Log.d("Leaderboard", "SENT!" + "PlayerName:" + playerName + "Score:" + score);
            }

            //dismiss();
        });

        buttonNo.setOnClickListener(v -> {
            GameActivity.instance.finish();
            dismiss();
        });

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void saveScore(String playerName, int score) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("Leaderboard", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(playerName, score); // Save score with player name
        editor.apply();
    }


}
