package org.techtown.myaroundme;

import android.Manifest;
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
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
    private ImageButton myInfo;
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
    private TextView south,north,west,east,east_south,west_south,south_seoul,center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        hotpost = (TextView) findViewById(R.id.hotpost);

        myInfo = (ImageButton)findViewById(R.id.myInfo);

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
                Intent writeIntent = new Intent(MainActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(MainActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MainActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        hotpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotIntent = new Intent(MainActivity.this,HotpostActivity.class);
                startActivity(hotIntent);
            }
        });

        isChecked = new boolean[8];
        //위치 권한 체크
        if(!isPermission)
            callLocationPermission();

        seoulmap=(LinearLayout) findViewById(R.id.seoulmap);

        south=(TextView)findViewById(R.id.south);
        north=(TextView)findViewById(R.id.north);
        west=(TextView)findViewById(R.id.west);
        east=(TextView)findViewById(R.id.east);
        east_south=(TextView)findViewById(R.id.east_south);
        west_south=(TextView)findViewById(R.id.west_south);
        south_seoul=(TextView)findViewById(R.id.south_seoul);
        center=(TextView)findViewById(R.id.center);
        accident=(TextView)findViewById(R.id.accident);
        crime=(TextView)findViewById(R.id.crime);
        fruit=(TextView)findViewById(R.id.fruit);
        local=(TextView)findViewById(R.id.local);

        final Drawable centerd = getResources().getDrawable(R.drawable.seoul_center);
        final Drawable nocenter = getResources().getDrawable(R.drawable.ic_seoul_districts);

        center.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[0]){
                    seoulmap.setBackgroundDrawable(centerd);
                    isChecked[0] = true;
                    local.setText("    도심     ");
                    accident.setText("  15 ");
                    crime.setText("     11 ");
                    fruit.setText("    250 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[0] = false;
                }
            }
        });

        final Drawable eastd = getResources().getDrawable(R.drawable.seoul_east);

        east.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[1]){
                    seoulmap.setBackgroundDrawable(eastd);
                    isChecked[1] = true;
                    local.setText("   동서울   ");
                    accident.setText("  27 ");
                    crime.setText("     15 ");
                    fruit.setText("    215 ");

                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[1] = false;
                }
            }
        });

        final Drawable east_southd = getResources().getDrawable(R.drawable.seoul_east_south);

        east_south.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[2]){
                    seoulmap.setBackgroundDrawable(east_southd);
                    isChecked[2] = true;
                    local.setText("    동남     ");
                    accident.setText("  13 ");
                    crime.setText("     11 ");
                    fruit.setText("    127 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[2] = false;
                }
            }
        });

        final Drawable northd = getResources().getDrawable(R.drawable.seoul_north);

        north.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[3]){
                    seoulmap.setBackgroundDrawable(northd);
                    isChecked[3] = true;
                    local.setText("    강북     ");
                    accident.setText("  20 ");
                    crime.setText("     17 ");
                    fruit.setText("    222 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[3] = false;
                }
            }
        });

        final Drawable southd = getResources().getDrawable(R.drawable.seoul_south);

        south.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[4]){
                    seoulmap.setBackgroundDrawable(southd);
                    isChecked[4] = true;
                    local.setText("    강남     ");
                    accident.setText("  27 ");
                    crime.setText("     15 ");
                    fruit.setText("    273 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[4] = false;
                }
            }
        });

        final Drawable south_seould = getResources().getDrawable(R.drawable.seoul_south_seoul);

        south_seoul.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[5]){
                    seoulmap.setBackgroundDrawable(south_seould);
                    isChecked[5] = true;
                    local.setText("   남서울   ");
                    accident.setText("  37 ");
                    crime.setText("     27 ");
                    fruit.setText("    325 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[5] = false;
                }
            }
        });
        final Drawable westd = getResources().getDrawable(R.drawable.seoul_west);

        west.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[6]){
                    seoulmap.setBackgroundDrawable(westd);
                    isChecked[6] = true;
                    local.setText("   서서울   ");
                    accident.setText("  24 ");
                    crime.setText("     12 ");
                    fruit.setText("    213 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[6] = false;
                }
            }
        });
        final Drawable west_southd = getResources().getDrawable(R.drawable.seoul_west_south);

        west_south.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[7]){
                    seoulmap.setBackgroundDrawable(west_southd);
                    isChecked[7] = true;
                    local.setText("    서남     ");
                    accident.setText("  29 ");
                    crime.setText("     11 ");
                    fruit.setText("    284 ");
                }
                else{
                    seoulmap.setBackgroundDrawable(nocenter);
                    isChecked[7] = false;
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(MainActivity.this,BoardActivity.class);
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
                Intent alarmIntent = new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });


        currentLocation();
        currentLocation.setText(location);
    }


    public void OnUserClick (View v){

        if(LoginActivity.isLogin){
            Intent myinfoIntent = new Intent(MainActivity.this,MyInfoActivity.class);
            startActivity(myinfoIntent);
        }

        else {
            Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(loginIntent);
        }
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
        gps = new MyLocation(MainActivity.this);

        if (gps.isGetLocation()) {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
        }
        else
            gps.showSettingsAlert();

        location = gps.getAddress(MainActivity.this, lat, lon);
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