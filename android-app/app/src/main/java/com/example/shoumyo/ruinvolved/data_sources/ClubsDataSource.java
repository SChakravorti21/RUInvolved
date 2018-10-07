package com.example.shoumyo.ruinvolved.data_sources;

import android.content.Context;

import com.example.shoumyo.ruinvolved.data_sources.services.ClubService;
import com.example.shoumyo.ruinvolved.data_sources.services.StudentService;
import com.example.shoumyo.ruinvolved.models.Category;
import com.example.shoumyo.ruinvolved.models.Club;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class ClubsDataSource {

    private ClubService service;

    public ClubsDataSource(Context context) {
        this.service = ServiceFactory.getService(context, ClubService.class);
    }

    public Single<List<String>> getAllCategories() {
        return service.getAllCategories()
                .toObservable()
                .flatMapIterable(categories -> categories)
                .map(category -> category.categoryName)
                .toList();
    }

    public Single<Club> getClubForId(String clubId) {
        return service.getClubForId(clubId);
    }

    public Single<List<Club>> getClubsForCategories(List<String> categories) {
        StringBuilder sb = new StringBuilder("[");
        String lastCategory = categories.get(categories.size() - 1);
        for(String category : categories) {
            sb.append(category);

            if(!category.equals(lastCategory))
                sb.append(",");
        }

        sb.append("]");
        String categoriesQueryString = sb.toString();
        return service.getClubsForCategories(categoriesQueryString);
    }


    public Single<List<Club>> getClubWithIds(List<String> ids) {

        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < ids.size(); i++) {

            sb.append(ids.get(i));

            if(i < ids.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");

        String idsQueryString = sb.toString();
        return null;

    }


}
