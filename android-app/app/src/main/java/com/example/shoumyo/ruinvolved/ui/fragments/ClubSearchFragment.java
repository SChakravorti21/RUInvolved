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
import com.example.shoumyo.ruinvolved.ui.MultiSelectionSpinner;
import com.example.shoumyo.ruinvolved.ui.adapters.ClubListAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClubSearchFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

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
                        this::setClubsFromDataSource,
                        error -> error.printStackTrace()
                );
    }

    public void setClubsFromDataSource(List<Club> clubs) {
        clubListAdapter.setDataSource(clubs);
        clubListAdapter.notifyDataSetChanged();
    }

    public void selectedIndices(@org.jetbrains.annotations.Nullable List<Integer> indices) {  }

}
