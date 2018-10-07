package com.example.shoumyo.ruinvolved;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoumyo.ruinvolved.models.Club;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ClubDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String CLUB_DETAILS_TAG = "club_details";

    private Club club;
    private boolean favorited;
    ImageButton favoriteButton;
    FloatingActionButton chatFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        this.club = (Club) getIntent().getSerializableExtra(CLUB_DETAILS_TAG);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.club_map);
        mapFragment.getMapAsync(this);

        favoriteButton = findViewById(R.id.club_favorite_action);
        favoriteButton.setOnClickListener(button -> {
            favorited = !favorited;
            updateFavoriteStar();

            if(favorited)
                SharedPrefsUtils.addFavoriteClub(this, club.id);
            else
                SharedPrefsUtils.removeFavoriteClub(this, club.id);
        });


        // starts the chat room activity
        chatFAB = findViewById(R.id.chat_fab);
        chatFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ClubDetailsActivity.this, ChatroomActivity.class);
                intent.putExtra(ChatroomActivity.CLUB_ID_TAG, club.id);

                String currentUser = SharedPrefsUtils.getUsername(ClubDetailsActivity.this);
                intent.putExtra(ChatroomActivity.USERNAME_TAG, currentUser);

                startActivity(intent);

            }
        });

        findViewById(R.id.map_update_fab).setOnClickListener(button -> {
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(MapsActivity.CLUB_EXTRA_TAG, this.club);
            startActivity(intent);
        });

        initializeClubDetails();
    }

    private void updateFavoriteStar() {
        favoriteButton.setImageResource(
                favorited
                ? R.drawable.ic_star_24dp
                : R.drawable.ic_star_border_24dp
        );
    }

    private void initializeClubDetails() {
        new LoadClubLogoTask().execute(club.getProfilePicture());
        ((TextView) findViewById(R.id.club_name)).setText(club.name);
        setHtmlText(R.id.club_summary, club.summary);
        setHtmlText(R.id.club_description, club.description);
        setCategories(club.categoryNames);

        favorited = SharedPrefsUtils.isFavorited(this, club.id);
        updateFavoriteStar();
    }

    private void setHtmlText(int viewId, String htmlText) {
        if(htmlText == null) return;

        TextView textView = findViewById(viewId);
        textView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT));
    }

    private void setCategories(List<String> categories) {
        String lastCategory = categories.get(categories.size() - 1);

        StringBuilder sb = new StringBuilder();
        for(String category : categories) {
            sb.append(category);

            if(!category.equals(lastCategory))
                sb.append(", ");
        }

        String categoriesList = sb.toString();
        ((TextView) findViewById(R.id.club_categories)).setText(categoriesList);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng clublocation = new LatLng(club.location.latitude, club.location.longitude);
        MarkerOptions marker = new MarkerOptions()
                .position(clublocation)
                .title(club.name);
        googleMap.addMarker(marker);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(clublocation));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
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
            ((ImageView) findViewById(R.id.club_logo)).setImageBitmap(bitmap);
        }
    }
}
