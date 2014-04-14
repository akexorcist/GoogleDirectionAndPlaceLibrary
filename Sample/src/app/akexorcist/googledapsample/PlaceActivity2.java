package app.akexorcist.googledapsample;

import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import app.akexorcist.gdaplibrary.GooglePlaceSearch;
import app.akexorcist.gdaplibrary.GooglePlaceSearch.OnPlaceResponseListener;
import app.akexorcist.gdaplibrary.PlaceType;

public class PlaceActivity2 extends FragmentActivity {
	final String ApiKey = "AIzaSyDQ6mA6vUHD3cMNqDoblES6q3dFHzNLqs4";
	
    double latitude = 13.730354;
	double longitude = 100.569701;
	int radius = 1000;
	String type = PlaceType.FOOD;
	String language = "en";

	GoogleMap mMap;
	GooglePlaceSearch gp;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_2);

        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));
        
        gp = new GooglePlaceSearch(ApiKey);
		gp.setOnPlaceResponseListener(new OnPlaceResponseListener() {
			public void onResponse(String status, ArrayList<ContentValues> arr_data, Document doc) {
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
								
				if(status.equals(GooglePlaceSearch.STATUS_OK)) {
					for(int i = 0 ; i < arr_data.size() ; i++) {
						String title = arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_NAME);
						double lat = arr_data.get(i).getAsDouble(GooglePlaceSearch.PLACE_LATITUDE);
						double lng = arr_data.get(i).getAsDouble(GooglePlaceSearch.PLACE_LONGITUDE);
						LatLng pos = new LatLng(lat, lng);
						
						mMap.addMarker(new MarkerOptions().position(pos).title(title));
					}
				}
			}
		});
		
        gp.getNearby(latitude, longitude, radius, type, language);
        //gp.getRadarSearch(latitude, longitude, radius, type, language, false);
	}
}
