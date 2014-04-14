package app.akexorcist.googledapsample;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.akexorcist.gdaplibrary.GoogleDirection;
import app.akexorcist.gdaplibrary.GoogleDirection.OnAnimateListener;
import app.akexorcist.gdaplibrary.GoogleDirection.OnDirectionResponseListener;

public class DirectionActivity3 extends FragmentActivity {
	LatLng start = new LatLng(13.744246499553903, 100.53428772836924);
	LatLng end = new LatLng(13.751279688694071, 100.54316081106663);

	TextView textProgress;
	Button buttonAnimate, buttonRequest;
	
	GoogleMap mMap;
    GoogleDirection gd;
    Document mDoc;
    
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_1);
        
        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
        
        gd = new GoogleDirection(this);
        gd.setOnDirectionResponseListener(new OnDirectionResponseListener() {
			public void onResponse(String status, Document doc, GoogleDirection gd) {
				mDoc = doc;
				mMap.addPolyline(gd.getPolyline(doc, 3, Color.RED));	
		        
		        buttonAnimate.setVisibility(View.VISIBLE);
			}
		});
        
        gd.setOnAnimateListener(new OnAnimateListener() {
			public void onStart() {
				textProgress.setVisibility(View.VISIBLE);
			}
			
			public void onProgress(int progress, int total) {
				textProgress.setText((int)((float)progress / total * 100) + "% / 100%");
			}
			
			public void onFinish() {
		        buttonAnimate.setVisibility(View.VISIBLE);
				textProgress.setVisibility(View.GONE);
			}
		});
        
        textProgress = (TextView)findViewById(R.id.textProgress);
        textProgress.setVisibility(View.GONE);
        
        buttonRequest = (Button)findViewById(R.id.buttonRequest);
        buttonRequest.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				gd.setLogging(true);
				gd.request(start, end, GoogleDirection.MODE_DRIVING);
			}
		});
        
        buttonAnimate = (Button)findViewById(R.id.buttonAnimate);
        buttonAnimate.setVisibility(View.GONE);
        buttonAnimate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				v.setVisibility(View.GONE);
				gd.animateDirection(mMap, gd.getDirection(mDoc), GoogleDirection.SPEED_VERY_SLOW
        				, true, false, true, true
        				, new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
        				, true, false, null);
			}
		});
	}
	
    public void onPause() {
    	super.onPause();
    	gd.cancelAnimated();
    }
}
