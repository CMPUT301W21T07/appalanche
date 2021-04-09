package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.team007.appalanche.R;
import com.team007.appalanche.user.User;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * This fragment is created when a user wants to ask a question about
 * an experiment. It contains a text field where the user can enter their question,
 * and it also contains two buttons: Cancel and Post.
 */


public class AddUserIDFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private FirebaseFirestore db;
    private boolean valid = true;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_userid,null);

        EditText newUserID = view.findViewById(R.id.addUserID);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // SET UP BUTTON
        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the field is empty

                String newID = "@" + newUserID.getText().toString();

                // Validate newID
                validateUserID(newID, new Validate() {
                    @Override
                    public void isUserExist(boolean exist) {
                        Log.d(TAG, Boolean.toString(exist));
                        if (exist) {
                            newUserID.requestFocus();
                            newUserID.setError("Sorry, that username already exists, please enter a different username");
                        }
                        else {
                            // User does not exist, but check if user has entered some text
                            if (newUserID.getText().toString().trim().equalsIgnoreCase("")) {
                                newUserID.setError("This field can not be blank");
                            }  else {  // If string is not empty -> dismiss
                                User newUser = new User(newID);
                                listener.addUserID(newUser);
                                dismiss();
                            }
                        }
                    }
                });
            }
        });

        return  builder
                .setView(view)
                .setTitle("NEW USER ID")
                .create();
    }

    public interface OnFragmentInteractionListener {
        void addUserID(User newUser);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    interface Validate {
        void isUserExist(boolean exist);
    }

    // QUERY THROUGH EVERY DOCUMENTS IN USERS COLLECTION, CHECK IF THE USER ID ALREADY EXISTED
    public void validateUserID(String newID, Validate validate) {
        db = FirebaseFirestore.getInstance();
        // Retrieve multiple documents from firebase
        Task<QuerySnapshot> col =  db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete (@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> documents = task.getResult().getDocuments();
                    boolean val = false;
                    for (DocumentSnapshot document : documents) {
                        Log.d(TAG, document.getId());
                        if (document.getId().matches(newID)) {
                            val = true;
                            break;
                        }
                    }
                    validate.isUserExist(val);
                } else {
                    Log.d(TAG, "No such collection");
                }

            }
        });

    }
}