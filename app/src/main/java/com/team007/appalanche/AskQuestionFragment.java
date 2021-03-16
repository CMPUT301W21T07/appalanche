package com.team007.appalanche;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class AskQuestionFragment extends DialogFragment  {
    private OnFragmentInteractionListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ask_question,null);


        EditText askQuestion = view.findViewById(R.id.askQuestion);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("NEW QUESTION")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // send the askQuestion back to QuestionActivity
                    }
                })

                .create();
    }

    public interface OnFragmentInteractionListener {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
