package ch.epfl.sweng.sweetsugar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephane on 2/4/2018.
 */

public class City {
    private final String cityName;
    private final String description;
    private int rating;
    private int photoId;
    private boolean recommended;

    public City(String cityName, String description, int rating, int photoId, boolean recommended) {
        this.cityName = cityName;
        this.description = description;
        this.rating = rating;
        this.photoId = photoId;
        this.recommended = recommended;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public int getPhotoId(){
        return photoId;
    }

    public boolean getCityRecommendation() {
        return recommended;
    }

}
