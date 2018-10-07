package com.example.shoumyo.ruinvolved.ui.viewholders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoumyo.ruinvolved.ClubDetailsActivity;
import com.example.shoumyo.ruinvolved.R;
import com.example.shoumyo.ruinvolved.models.Club;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClubViewHolder extends RecyclerView.ViewHolder {

    private Club club;
    private ImageView profilePicture;
    private TextView clubName;
    private TextView clubDescription;

    public ClubViewHolder(@NonNull View itemView) {
        super(itemView);
        this.profilePicture = itemView.findViewById(R.id.club_logo);
        this.clubName = itemView.findViewById(R.id.club_name);
        this.clubDescription = itemView.findViewById(R.id.club_description);

        itemView.setOnClickListener(v -> {
            Context context = itemView.getContext();
            Intent intent = new Intent(context, ClubDetailsActivity.class);
            intent.putExtra(ClubDetailsActivity.CLUB_DETAILS_TAG, club);
            context.startActivity(intent);
        });
    }

    public void setClub(Club club) {
        this.club = club;
        this.setClubName(club.name);
        this.setClubDescription(club.description);
        this.setProfilePicture(club.getProfilePicture());
    }

    private void setProfilePicture(String imageUrl) {
        Picasso.get().load(imageUrl).into(profilePicture);
    }

    private void setClubName(String clubName) {
        clubName = clubName.trim();
        this.clubName.setText(clubName);
    }

    private void setClubDescription(String description) {
        if(description == null)
            return;

        description = description.trim();
        this.clubDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
    }

    private class LoadClubLogoTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            if(strings.length == 0)
                return null;

            Bitmap bitmap = null;
            String imageUrl = strings[0];
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageUrl).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            profilePicture.setImageBitmap(bitmap);
        }
    }

}
