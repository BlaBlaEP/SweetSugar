package ch.epfl.sweng.sweetsugar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    private RecyclerView rv;
    private LinearLayoutManager llm;
    private CitiesAdapter cad;
    private List<City> cities;
    private ImageView appBarImage;
    private SearchView mPlacesSearchView;
    private Button mGoToSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        appBarImage = findViewById(R.id.app_bar_image);
        mGoToSearchButton = findViewById(R.id.go_to_search_places_button);
        mPlacesSearchView = findViewById(R.id.my_places_search_view);

        mPlacesSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cad.filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cad.filter(s);
                return false;
            }
        });

        appBarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(appBarImage);
            }
        });

        rv = (RecyclerView) findViewById(R.id.cityRV);
        //rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);

        initializeData();

        cad = new CitiesAdapter(cities);

        rv.setAdapter(cad);

    }

    public void initializeData() {
        cities = new ArrayList<>();
        //cities.add(new City("Lausanne", "", 4, R.drawable.lausanne, true));
        cities.add(new City("Geneva", "", 3, R.drawable.geneva, false));
        cities.add(new City("Zurich", "", 4, R.drawable.zurich, true));
        cities.add(new City("Bern", "", 5, R.drawable.bern, true));

    }

    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getApplicationContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_places, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuPlacesClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuPlacesClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuPlacesClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.go_to_music:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                default:
            }
            return false;
        }
    }

    public void goToPlacesSearchActivity(View view) {
        Intent intent = new Intent(this, CitySearchActivity.class);
        startActivity(intent);
    }

    public void goToCityPhotoActivity(View view) {
        Intent intent = new Intent(this, CityPhotoActivity.class);
        startActivity(intent);
    }

    public void addToPlaces(View view) {
        cad.addCity();
    }

}
