package com.example.shoumyo.ruinvolved.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.data_sources.ClubsDataSource;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Set;

public class ClubListFragment extends Fragment {

    private ArrayList<Club> clubsToShow;
    private ClubsDataSource dataSource;

    public ClubListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ClubsDataSource(getContext());

        clubsToShow = new ArrayList<>();
        Set<String> favoritedClubs = SharedPrefsUtils.getFavoritedClubs(getContext());

        for(String id : favoritedClubs) {

        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_list, container, false);

        RecyclerView clubsRecycler = view.findViewById(R.id.clubs_recycler);
        clubsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return null;
    }
}
