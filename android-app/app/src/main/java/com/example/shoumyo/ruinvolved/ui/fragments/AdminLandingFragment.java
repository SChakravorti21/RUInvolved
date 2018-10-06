package com.example.shoumyo.ruinvolved.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoumyo.ruinvolved.ClubDetailsActivity;
import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.StudentActivity;
import com.example.shoumyo.ruinvolved.data_sources.ClubsDataSource;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;

public class AdminLandingFragment extends Fragment {

    ClubsDataSource clubsDataSource;
    EditText clubId, username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.clubsDataSource = new ClubsDataSource(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_admin_landing, container, false);

        this.clubId = viewRoot.findViewById(R.id.club_genid);
        this.username = viewRoot.findViewById(R.id.display_name);

        Button signInButton = viewRoot.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(button -> ensureValidClubId());

        return viewRoot;
    }

    @SuppressLint("CheckResult")
    private void ensureValidClubId() {
        clubsDataSource.getClubForId(clubId.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadClubDetailsPage,
                        error -> {
                            error.printStackTrace();
                            Toast.makeText(getContext(),
                                    "Unauthorized, please double check your club ID.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                );
    }

    private void loadClubDetailsPage(Club club) {
        Context context = getContext();
        String name = username.getText().toString();
        if(!validateUsername(name))
            return;

        SharedPrefsUtils.setUsername(context, name);
        Intent activityIntent = new Intent(context, ClubDetailsActivity.class);
        activityIntent.putExtra(ClubDetailsActivity.CLUB_DETAILS_TAG, club);
        context.startActivity(activityIntent);
    }

    private boolean validateUsername(String username) {
        if(username == null || username.isEmpty()) {
            Toast error = Toast.makeText(getContext(), "Please enter a valid name", Toast.LENGTH_LONG);
            error.show();
            return false;
        }

        return true;
    }

}
