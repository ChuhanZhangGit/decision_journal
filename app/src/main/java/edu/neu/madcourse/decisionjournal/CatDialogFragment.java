package edu.neu.madcourse.decisionjournal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CatDialogFragment extends DialogFragment {

    private static final List<String> pussyWordsList = Arrays.asList(
            "Meow, You're not alone.",
            "Meow, I'm your #1 fan!",
            "Paw, It's ok to take a break.",
            "Paw, How can I help?",
//            "Your feelings are valid.",
//            "You've done it before!",
            "Stroke, Focus on one thing at a time.",
            "Mew, I'm here if you want to talk."

    );
    private TextView catWordView;

    public static CatDialogFragment getInstance() {
        return new CatDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.cat_fragment, null);
        catWordView = view.findViewById(R.id.cat_word_textView);


        Random random = new Random();
        int catWordIdx = random.nextInt(pussyWordsList.size());
        catWordView.setText(pussyWordsList.get(catWordIdx));
        builder.setView(view)
                .setTitle("Hey it's me, Kit Kat")
                .setPositiveButton("Got you!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CatDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
