package app.akexorcist.googledapsample;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import app.akexorcist.gdaplibrary.GoogleDirection;
import app.akexorcist.gdaplibrary.GoogleDirection.OnDirectionResponseListener;

public class DirectionActivity2 extends FragmentActivity {
	
	LatLng start = new LatLng(13.744246499553903, 100.53428772836924);
	LatLng end = new LatLng(13.751279688694071, 100.54316081106663);

	TextView textProgress;
	Button buttonAnimate, buttonRequest;
	
	GoogleMap mMap;
    GoogleDirection gd;
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_2);
        
        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
        
        gd = new GoogleDirection(this);
        gd.setOnDirectionResponseListener(new OnDirectionResponseListener() {
			public void onResponse(String status, Document doc, GoogleDirection gd) {	
				Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
		        
				gd.animateDirection(mMap, gd.getDirection(doc), GoogleDirection.SPEED_NORMAL
        				, true, true, true, false, null, false, true, new PolylineOptions().width(3));
			}
		});
        
		gd.request(start, end, GoogleDirection.MODE_DRIVING);
	}
	
    public void onPause() {
    	super.onPause();
    	gd.cancelAnimated();
    }
}
