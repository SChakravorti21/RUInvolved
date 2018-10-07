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
import com.example.shoumyo.ruinvolved.ui.adapters.ClubListAdapter;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClubListFragment extends Fragment {

    private ClubsDataSource dataSource;

    public ClubListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ClubsDataSource(getContext());


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_list, container, false);

        RecyclerView clubsRecycler = view.findViewById(R.id.clubs_recycler);
        clubsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Set<String> favoritedClubs = SharedPrefsUtils.getFavoritedClubs(getContext());

        List<Integer> ids = favoritedClubs.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());


        dataSource.getClubWithIds(ids)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clubs -> {

                    ClubListAdapter adapter = new ClubListAdapter(clubs);
                    clubsRecycler.setAdapter(adapter);

                }, error -> error.printStackTrace());


        return null;
    }
}
