package org.techtown.myaroundme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button check1, check2;
    private Button home, board, map;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen=false;
    private FloatingActionButton fab, fab1,fab2,fab3;

    ImageView gray;
    TextView fabText1,fabText2,fabText3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);

        fabText1 = (TextView)findViewById(R.id.fabtext1);
        fabText2 = (TextView)findViewById(R.id.fabtext2);
        fabText3 = (TextView)findViewById(R.id.fabtext3);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(NotificationActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(NotificationActivity.this,BoardActivity.class);
                startActivity(boardIntent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("lat", MainActivity.lat);
                intent.putExtra("lon", MainActivity.lon);
                intent.putExtra("location", MainActivity.location);
                startActivity(intent);
            }
        });

        fab.setOnClickListener((View.OnClickListener) this);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(NotificationActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(NotificationActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(NotificationActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        gray = (ImageView)findViewById(R.id.grayback);

        check1 = findViewById(R.id.checkalarm);
        check2 = findViewById(R.id.checkmail);

        final String blackColor = "#000000";
        final String grayColor = "#8C8C8C";

        final Drawable checked = getResources().getDrawable(R.drawable.border_black_bottom);
        final Drawable notchecked = getResources().getDrawable(R.drawable.border_gray_bottom);

        check1.setOnClickListener(new Button.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                check1.setTextColor(Color.parseColor((blackColor)));
                check1.setBackgroundDrawable(checked);
                check2.setTextColor(Color.parseColor((grayColor)));
                check2.setBackgroundDrawable(notchecked);
            }
        });

        check2.setOnClickListener(new Button.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            public void onClick(View v) {
                check2.setTextColor(Color.parseColor((blackColor)));
                check2.setBackgroundDrawable(checked);
                check1.setTextColor(Color.parseColor((grayColor)));
                check1.setBackgroundDrawable(notchecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
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
}