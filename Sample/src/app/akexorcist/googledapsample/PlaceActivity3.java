package app.akexorcist.googledapsample;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.akexorcist.gdaplibrary.GooglePlaceSearch;
import app.akexorcist.gdaplibrary.GooglePlaceSearch.OnBitmapResponseListener;
import app.akexorcist.gdaplibrary.GooglePlaceSearch.OnPlaceResponseListener;
import app.akexorcist.gdaplibrary.PlaceType;

public class PlaceActivity3 extends Activity {
	
	final String ApiKey = "AIzaSyDQ6mA6vUHD3cMNqDoblES6q3dFHzNLqs4";
	
    double latitude = 13.730354;
	double longitude = 100.569701;
	int radius = 1000;
	String type = PlaceType.FOOD;
	String language = "en";
	String keyword = "japan restaurant food";
	
	TextView textStatus;
	ListView listView;
	
	GooglePlaceSearch gp;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_1);

        textStatus = (TextView)findViewById(R.id.textStatus);
        
        listView = (ListView)findViewById(R.id.listView);
        
        gp = new GooglePlaceSearch(ApiKey);
		gp.setOnPlaceResponseListener(new OnPlaceResponseListener() {
			public void onResponse(String status, ArrayList<ContentValues> arr_data,
					Document doc) {
				textStatus.setText("Status : " + status);
				
				if(status.equals(GooglePlaceSearch.STATUS_OK)) {
					ArrayList<String> array = new ArrayList<String>();
					final ArrayList<String> array_photo = new ArrayList<String>();
					
					for(int i = 0 ; i < arr_data.size() ; i++) {
						array.add("Name : " + arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_NAME) + "\n"
								+ "Address : " + arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_ADDRESS) + "\n"
								+ "Latitude : " + arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_LATITUDE) + "\n"
								+ "Longitude : " + arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_LONGITUDE) + "\n"
								+ "Phone Number : " + arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_PHONENUMBER));
						array_photo.add(arr_data.get(i).getAsString(GooglePlaceSearch.PLACE_PHOTO));
					}

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlaceActivity3.this
							, R.layout.listview_text, array);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							Dialog dialog = new Dialog(PlaceActivity3.this);
			                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			                dialog.setContentView(R.layout.dialog_photo);
			                dialog.setCancelable(true);

			                final ImageView imgPhoto = (ImageView)dialog.findViewById(R.id.imgPhoto);

			                dialog.show();
			                
							gp.getPhotoBitmapByWidth(array_photo.get(arg2), 600, ""
									, new OnBitmapResponseListener() {
								public void onResponse(Bitmap bm, String tag) {
					                imgPhoto.setImageBitmap(bm);
								}
							});
						}
					});
				}
			}
		});
		
        gp.getNearby(latitude, longitude, radius, type, language, keyword);
	}
}
