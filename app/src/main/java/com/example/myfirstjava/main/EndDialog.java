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

public class EndDialog extends DialogFragment {
    private static boolean _isShowing = false;
    public static boolean isShowing(){return _isShowing; }
    public static void create(){
        _isShowing = false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        _isShowing = true;
        GameActivity.instance.setTimeScale(0);

        // Inflate the custom layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.endui, null);

        // Set up buttons
        Button buttonNo = view.findViewById(R.id.mainmenu_button);

        buttonNo.setOnClickListener(v -> {
            GameActivity.instance.finish();
            dismiss();
        });

        // Build dialog
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(view);
        dialog.setCancelable(false); // Disable canceling by tapping outside
        dialog.setCanceledOnTouchOutside(false); // Disable canceling by touching outside
        return dialog;
    }



}
