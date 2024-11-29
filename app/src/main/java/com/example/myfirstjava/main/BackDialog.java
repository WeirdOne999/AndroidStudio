package com.example.myfirstjava.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstjava.mgp2d.core.GameActivity;

public class BackDialog extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing(){return _isShowing; }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Return?");
        builder.setPositiveButton("Yes", (dialog, which) -> GameActivity.instance.finish());
        builder.setNegativeButton("No", null);
        return builder.create();
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
