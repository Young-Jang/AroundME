package org.techtown.myaroundme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotpostActivity extends AppCompatActivity implements View.OnClickListener {
    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    private MyLocation gps;
    private Button home,board,map,alarm;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1,fab2,fab3;
    private boolean isChecked[];

    LinearLayout seoulmap;
    ImageView gray;
    TextView fabText1,fabText2,fabText3;
    TextView local;
    TextView accident, crime, fruit,hotpost;
    TextView currentLocation;

    static public double lon;
    static public double lat;

    static String location = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotpost);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);

        fabText1 = (TextView)findViewById(R.id.fabtext1);
        fabText2 = (TextView)findViewById(R.id.fabtext2);
        fabText3 = (TextView)findViewById(R.id.fabtext3);
        currentLocation = findViewById(R.id.location);

        fab.setOnClickListener((View.OnClickListener) this);

        gray = (ImageView)findViewById(R.id.grayback);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(HotpostActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(HotpostActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(HotpostActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        isChecked = new boolean[8];
        //위치 권한 체크
        if(!isPermission)
            callLocationPermission();

        seoulmap=(LinearLayout) findViewById(R.id.seoulmap);

        accident=(TextView)findViewById(R.id.accident);
        crime=(TextView)findViewById(R.id.crime);
        fruit=(TextView)findViewById(R.id.fruit);
        local=(TextView)findViewById(R.id.local);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(HotpostActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(HotpostActivity.this,BoardActivity.class);
                startActivity(boardIntent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                intent.putExtra("location", location);
                startActivity(intent);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(HotpostActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });


        currentLocation();
        currentLocation.setText(location);
    }
    @Override
    public void onClick(View v)
    {
        int id=v.getId();
        switch (id){
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                break;
            case R.id.fab2:
                anim();
                break;
        }

    }
    // 애니메이션 효과
    public void anim(){
        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);

            gray.setVisibility(View.INVISIBLE);
            fabText1.setVisibility(View.INVISIBLE);
            fabText2.setVisibility(View.INVISIBLE);
            fabText3.setVisibility(View.INVISIBLE);

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);

            gray.setVisibility(View.VISIBLE);
            fabText1.setVisibility(View.VISIBLE);
            fabText2.setVisibility(View.VISIBLE);
            fabText3.setVisibility(View.VISIBLE);

            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }
    //현재 위치를 알아오는 메소드
    public void currentLocation(){
        gps = new MyLocation(HotpostActivity.this);

        if (gps.isGetLocation()) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        }
        else
            gps.showSettingsAlert();

        location = gps.getAddress(HotpostActivity.this, lat, lon);
    }
    // 권한 요청 결과
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    //권한 요청
    private void callLocationPermission() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }
}