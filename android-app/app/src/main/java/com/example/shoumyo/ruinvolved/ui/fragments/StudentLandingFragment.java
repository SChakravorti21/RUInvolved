package com.example.shoumyo.ruinvolved.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.StudentActivity;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

public class StudentLandingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_student_landing, container, false);

        EditText username = viewRoot.findViewById(R.id.display_name);
        Button signInButton = viewRoot.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(button -> {
            Context context = getContext();
            String name = username.getText().toString();
            if(!validateUsername(name))
                return;

            SharedPrefsUtils.setUsername(context, name);
            SharedPrefsUtils.setIsAdmin(context, false);

            Intent activityIntent = new Intent(context, StudentActivity.class);
            context.startActivity(activityIntent);
        });

        Picasso.get()
                .load("https://music.colostate.edu/wp-content/uploads/sites/17/2018/08/09.20.18-University-Symphony-Orchestra.jpg")
                .into((ImageView) viewRoot.findViewById(R.id.header_image));

        return viewRoot;
    }

    public boolean validateUsername(String username) {
        if(username == null || username.isEmpty()) {
            Toast error = Toast.makeText(getContext(), "Please enter a valid name", Toast.LENGTH_LONG);
            error.show();
            return false;
        }

        return true;
    }
}
