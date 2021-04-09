package com.team007.appalanche.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.team007.appalanche.R;
import com.team007.appalanche.user.ContactInfo;
import com.team007.appalanche.user.Profile;
import com.team007.appalanche.user.User;

public class EditUserInfoFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private int k;

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_user_info, null);

        User user = (User) getArguments().getSerializable("user");
        EditText userName = view.findViewById(R.id.editUserName);
        userName.setText(user.getProfile().getUserName());
        EditText phoneNumber = view.findViewById(R.id.editPhoneNumber);
        if (user.getProfile().getContactInfo().getPhoneNumber() != null)
            phoneNumber.setText(user.getProfile().getContactInfo().getPhoneNumber().toString());
        EditText githubLink = view.findViewById(R.id.editGithubLink);
        githubLink.setText(user.getProfile().getContactInfo().getGithubLink());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("EDIT USER INFO")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // NEED TO CHANGE THE USER AFTER CONNECTING TO THE DATABASE
                        User user = (User) getArguments().getSerializable("user");
                        ContactInfo contactInfo;
                        // Integer phoneNum = phoneNumber.getText();
                        if (!phoneNumber.getText().toString().equals("")) {
                            contactInfo = new ContactInfo(phoneNumber.getText().toString(), githubLink.getText().toString());
                        }
                        else {
                            contactInfo = new ContactInfo("", githubLink.getText().toString());
                        }

                        Profile profile = new Profile(userName.getText().toString(), contactInfo);
                        user.setProfile(profile);
                        listener.updateUserInfo(user);
                    }
                })
                .create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_user_info, container, false);
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

    public interface OnFragmentInteractionListener {
        void updateUserInfo(User user);
    }

    public static EditUserInfoFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("user", user);
        EditUserInfoFragment fragment = new EditUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
