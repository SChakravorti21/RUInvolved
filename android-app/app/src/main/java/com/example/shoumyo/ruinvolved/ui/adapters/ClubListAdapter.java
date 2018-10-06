package com.example.shoumyo.ruinvolved.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoumyo.ruinvolved.ClubDetailsActivity;
import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.StudentActivity;
import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.ui.viewholders.ClubViewHolder;

import java.util.List;

public class ClubListAdapter extends RecyclerView.Adapter<ClubViewHolder> {

    private List<Club> clubs;

    public ClubListAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    public void setDataSource(List<Club> clubs) {
        this.clubs = clubs;
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View rootView = inflater.inflate(R.layout.rv_club_item, null);
        return new ClubViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder clubViewHolder, int i) {
        Club club = clubs.get(i);
        clubViewHolder.setClub(club);
    }

    @Override
    public int getItemCount() {
        return (clubs != null) ? clubs.size() : 0;
    }
}
