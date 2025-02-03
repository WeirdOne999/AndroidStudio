package com.example.myfirstjava.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstjava.R;
import com.example.myfirstjava.mgp2d.core.GameActivity;

public class BackDialog extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing(){return _isShowing; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pauseui, null);

        Button buttonNo = view.findViewById(R.id.mainmenu_button);
        Button buttonNo1 = view.findViewById(R.id.resume_button);

        buttonNo.setOnClickListener(v -> {
            UIEntity.instance.saveHighScore(GameActivity.instance, MainGameScene.instance.Score);
            GameActivity.instance.finish();

            dismiss();
        });

        buttonNo1.setOnClickListener(v -> {
            dismiss();
            GameActivity.instance.setTimeScale(1);
        });


        // Build dialog
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(view);
        dialog.setCancelable(false); // Disable canceling by tapping outside
        dialog.setCanceledOnTouchOutside(false); // Disable canceling by touching outside
        return dialog;
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setMessage("Return?");
//        builder.setPositiveButton("Yes", (dialog, which) -> GameActivity.instance.finish());
//        builder.setNegativeButton("No", null);
//        return builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        _isShowing = false;
        GameActivity.instance.setTimeScale(1);
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        _isShowing = false;
        GameActivity.instance.setTimeScale(1);
    }
}
