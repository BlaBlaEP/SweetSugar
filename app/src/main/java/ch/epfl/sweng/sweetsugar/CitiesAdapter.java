package ch.epfl.sweng.sweetsugar;

import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephane on 2/4/2018.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {

    List<City> cities;
    List<City> citiesCopy;

    public class CityViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView cityName;
        ImageView cityImage;
        RatingBar ratingBar;
        ToggleButton toggleButton;

        public CityViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.CityCV);
            cityName = (TextView) itemView.findViewById(R.id.cityNameTextView);
            cityImage = (ImageView) itemView.findViewById(R.id.cityImage);
            ratingBar = (RatingBar) itemView.findViewById(R.id.cityRatingBar);
            toggleButton = (ToggleButton) itemView.findViewById(R.id.toggleButton);
        }
    }

    public CitiesAdapter(List<City> cities) {
        this.cities = new ArrayList<>(cities);
        this.citiesCopy = new ArrayList<>(cities);
    }

    public void filter(String text) {
        cities.clear();
        if(text.isEmpty()) {
            cities.addAll(citiesCopy);
        } else {
            text = text.toLowerCase();
            for(City city : citiesCopy) {
                if(city.getCityName().toLowerCase().contains(text)) {
                    cities.add(city);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.city_card, viewGroup, false);
        CityViewHolder cvh = new CityViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CityViewHolder cvh, int i) {
        cvh.cityName.setText(cities.get(i).getCityName());
        if(cities.get(i).getCityPhoto() == null) {
            cvh.cityImage.setImageResource(cities.get(i).getPhotoId());
        } else {
            cvh.cityImage.setImageBitmap(cities.get(i).getCityPhoto());
        }
        cvh.ratingBar.setRating(cities.get(i).getRating());
        cvh.toggleButton.setChecked(cities.get(i).getCityRecommendation());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addCity() {
        cities.add(new City("Lausanne", "", 4, R.drawable.lausanne, true));
        notifyItemInserted(cities.size()-1);
    }

    public void addSpecificCity(CharSequence cityName, Bitmap placePhoto) {
        cities.clear();
        if(placePhoto == null) {
            cities.add(new City(cityName.toString(), "", 4, R.drawable.lausanne, true));
        } else {
            cities.add(new City(cityName.toString(), "", 4, placePhoto, true));
        }
        notifyDataSetChanged();
    }
}
