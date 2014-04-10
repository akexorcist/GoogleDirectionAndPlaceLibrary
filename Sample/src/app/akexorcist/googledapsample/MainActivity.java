package app.akexorcist.googledapsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	Button buttonDirection = (Button)findViewById(R.id.buttonDirection);
    	buttonDirection.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, DirectionActivity.class);
				startActivity(intent);
			}
		});
        
    	Button buttonPlace = (Button)findViewById(R.id.buttonPlace);
    	buttonPlace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
				startActivity(intent);
			}
		});
    }
}
