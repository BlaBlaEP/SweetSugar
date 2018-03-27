package ch.epfl.sweng.sweetsugar;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CityPhotoActivity extends AppCompatActivity {

    private static final String TAG = "CityPhotoActivity";

    private GeoDataClient mGeoDataClient;
    private PlacePhotoMetadata photoMetadata;
    private Bitmap photoBitmap;

    private ImageView cityPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_photo);

        cityPhoto = findViewById(R.id.CityPhotoImageView);

        mGeoDataClient = Places.getGeoDataClient(this, null);
        if(mGeoDataClient == null) {
            Log.d(TAG, "This won't work");
        }

        Log.d(TAG, "We're here");

        getPhotos();
        //cityPhoto.setImageResource(R.drawable.london);
    }

    private void getPhotos() {
        final String placeId = "ChIJa147K9HX3IAR-lwiGIQv9i4";
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mGeoDataClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();
                // Get the first photo in the list.
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);
                // Get the attribution text.
                CharSequence attribution = photoMetadata.getAttributions();
                // Get a full-size bitmap for the photo.
                Task<PlacePhotoResponse> photoResponse = mGeoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                        PlacePhotoResponse photo = task.getResult();
                        photoBitmap = photo.getBitmap();
                        Log.d(TAG, "Got the photo?");
                        cityPhoto.setImageBitmap(photoBitmap);
                        Log.d(TAG, "Should be done by now");
                    }
                });
            }
        });
    }
}
