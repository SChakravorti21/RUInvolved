package com.example.shoumyo.ruinvolved.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.data_sources.ClubsDataSource;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.ui.MultiSelectionSpinner;
import com.example.shoumyo.ruinvolved.ui.adapters.ClubListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClubSearchFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private List<Club> allClubs;
    private ClubsDataSource clubDataSource;
    private ClubListAdapter clubListAdapter;
    private MultiSelectionSpinner selectionSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.clubDataSource = new ClubsDataSource(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_club_search, container, false);

        RecyclerView clubListRecyclerView = root.findViewById(R.id.club_list_recyclerview);
        clubListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        this.clubListAdapter = new ClubListAdapter(null);
        clubListRecyclerView.setAdapter(clubListAdapter);
        this.initializeMultiSelect(root);

        EditText clubSearch = root.findViewById(R.id.clubSearchQuery);
        clubSearch.addTextChangedListener(new TextWatcher() {
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable;

            public void onTextChanged(final CharSequence s, int start, final int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(final Editable s) {
                String query = clubSearch.getText().toString();
                //do some work with s.toString()
                List<Club> filteredClubs = allClubs
                        .stream()
                        .filter(club -> club.name != null && club.name.contains(query))
                        .collect(Collectors.toList());

                //show some progress, because you can access UI here
                runnable = () -> setClubsFromDataSource(filteredClubs);
                handler.postDelayed(runnable, 500);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        return root;
    }

    @SuppressLint("CheckResult")
    private void initializeMultiSelect(View viewRoot) {
        selectionSpinner = viewRoot.findViewById(R.id.categories);
        selectionSpinner.setListener(this);

        clubDataSource.getAllCategories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> {
                            String[] category_names = new String[categories.size()];
                            selectionSpinner.setItems( categories.toArray(category_names) );
                        },
                        error -> error.printStackTrace()
                );
    }

    @SuppressLint("CheckResult")
    public void selectedStrings(@Nullable List<String> categories) {
        clubDataSource.getClubsForCategories(categories)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clubs -> {
                            this.allClubs = clubs;
                            setClubsFromDataSource(clubs);
                        },
                        error -> error.printStackTrace()
                );
    }

    public void setClubsFromDataSource(List<Club> clubs) {
        clubListAdapter.setDataSource(clubs);
        clubListAdapter.notifyDataSetChanged();
    }

    public void selectedIndices(List<Integer> indices) {  }
}
