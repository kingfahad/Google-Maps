package com.example.map;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapMain extends FragmentActivity implements OnMapReadyCallback{

	private EditText latitude;
	private EditText longitude;
	private Button add;

	private LatLng position;
	private MapFragment mapFragment;
	private PolylineOptions lines;
	
	private double min;
	private double max;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		latitude = (EditText) findViewById(R.id.lat_field);
		longitude = (EditText) findViewById(R.id.long_field);
		add = (Button) findViewById(R.id.add_btn);

		mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);

		lines = new PolylineOptions();
		lines.geodesic(true);

		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(latitude.getText().length() > 0 && longitude.getText().length() > 0){
					position = new LatLng(
							Double.parseDouble(latitude.getText().toString()), 
							Double.parseDouble(longitude.getText().toString()));

					lines.add(position);
					
					if(min > position.latitude)
						min = position.latitude;
					
					if(max < position.longitude);
						max = position.longitude;
						
					mapFragment.getMapAsync(MapMain.this);

					latitude.setText("");
					longitude.setText("");
				}
			}
		});
	}

	@Override
	public void onMapReady(GoogleMap map) {
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(min, max), 2));
		map.addMarker(new MarkerOptions().position(position));

		// Polylines are useful for marking paths and routes on the map.
		map.addPolyline(lines);
	}
}
