package org.techtown.myaroundme;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private double longitude = 37.504394;
    private double latitude = 126.956986; //lon = 37.504394 lat =126.956986

    private GoogleMap mMap;
    private ImageButton btnShowLocation;
    private TextView location;
    private MyLocation gps;
    private String currentLocation;
    private Button home, board, alarm;
    private ImageButton searchButton;

    LinearLayout boxMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        longitude = intent.getExtras().getDouble("lon");
        latitude = intent.getExtras().getDouble("lat");
        currentLocation = intent.getExtras().getString("location");

        location = findViewById(R.id.maplocation);
        location.setText(currentLocation);

        boxMap = (LinearLayout) findViewById(R.id.boxMap);

        btnShowLocation = findViewById(R.id.gps);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                gps = new MyLocation(MapsActivity.this);
                if (gps.isGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(),
                            "위도: " + latitude + "\n경도: " + longitude, Toast.LENGTH_LONG).show();

                    location.setText(gps.getAddress(MapsActivity.this, latitude, longitude));
                }
                else
                    gps.showSettingsAlert();
            }
        });

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        alarm = (Button)findViewById(R.id.alarm);

        searchButton = (ImageButton)findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchButtonIntent = new Intent(MapsActivity.this,SearchActivity.class);
                startActivity(searchButtonIntent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MapsActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(MapsActivity.this,BoardActivity.class);
                startActivity(boardIntent);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(MapsActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng korea = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(korea).title("현재위치"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea,16));
        //boxMap.setVisibility(View.VISIBLE);
    }
}
