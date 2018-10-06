package com.example.shoumyo.ruinvolved;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoumyo.ruinvolved.models.Club;
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

public class ClubDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String CLUB_DETAILS_TAG = "club_details";

    private Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);
        this.club = (Club) getIntent().getSerializableExtra(CLUB_DETAILS_TAG);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.club_map);
        mapFragment.getMapAsync(this);
        initializeClubDetails();
    }

    private void initializeClubDetails() {
        new LoadClubLogoTask().execute(club.getProfilePicture());
        ((TextView) findViewById(R.id.club_name)).setText(club.name);
        setHtmlText(R.id.club_summary, club.summary);
        setHtmlText(R.id.club_description, club.description);
        setCategories(club.categoryNames);
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
        LatLng markerPosition = new LatLng(40.502573, -74.452631);
        MarkerOptions marker = new MarkerOptions()
                .position(markerPosition)
                .title(this.club.name);
        googleMap.addMarker(marker);

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(markerPosition)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10000, 10000, 10));
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
