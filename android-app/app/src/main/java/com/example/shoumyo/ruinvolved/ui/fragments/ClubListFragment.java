package com.example.shoumyo.ruinvolved.ui.fragments;

import android.annotation.SuppressLint;
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

    private ClubListAdapter adapter;
    private ClubsDataSource dataSource;

    public ClubListFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSource = new ClubsDataSource(getContext());
    }


    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club_list, container, false);

        Set<String> favoritedClubs = SharedPrefsUtils.getFavoritedClubs(getContext());
        List<Integer> ids = favoritedClubs != null
            ? favoritedClubs.stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList())
            : new ArrayList<>();

        RecyclerView clubsRecycler = view.findViewById(R.id.clubs_recycler);
        clubsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ClubListAdapter(null);
        clubsRecycler.setAdapter(adapter);

        dataSource.getClubWithIds(ids)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(clubs -> {
                    adapter.setDataSource(clubs);
                    adapter.notifyDataSetChanged();
                }, error -> error.printStackTrace());

        return view;
    }
}
