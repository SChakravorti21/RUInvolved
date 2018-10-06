package com.example.shoumyo.ruinvolved;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.data_sources.ClubsDataSource;
import com.example.shoumyo.ruinvolved.ui.MultiSelectionSpinner;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StudentActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    @Nullable
    private ClubsDataSource clubDataSource;
    private MultiSelectionSpinner selectionSpinner;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_student);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            return true;
                        case R.id.navigation_dashboard:
                            return true;
                        case R.id.navigation_notifications:
                            return true;
                    }

                    return false;
                }
        );

        this.clubDataSource = new ClubsDataSource(this);
        this.initializeMultiSelect();
    }

    @SuppressLint("CheckResult")
    private void initializeMultiSelect() {
        selectionSpinner = findViewById(R.id.categories);
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

    public void selectedIndices(@Nullable List<Integer> indices) {

    }

    @SuppressLint("CheckResult")
    public void selectedStrings(@Nullable List<String> categories) {
        clubDataSource.getClubsForCategories(categories)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        clubs -> clubs.get(0),
                        error -> error.printStackTrace()
                );
    }
}
