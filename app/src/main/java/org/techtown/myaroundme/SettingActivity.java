package org.techtown.myaroundme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton backButton;
    EditText editText;
    TextView textView;
    private Button home,board,map,alarm;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen=false;
    private FloatingActionButton fab, fab1,fab2,fab3;
    ImageView gray;
    TextView fabText1,fabText2,fabText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        backButton=(ImageButton)findViewById(R.id.backButton);

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
                Intent homeIntent = new Intent(SettingActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(SettingActivity.this,BoardActivity.class);
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
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(SettingActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener((View.OnClickListener) this);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(SettingActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(SettingActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(SettingActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        gray = (ImageView)findViewById(R.id.grayback);

    }


    public void OnLocalPopupClick (View v){
        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.activity_select3, null);
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setView(dialogView);

        Button l1 = dialogView.findViewById(R.id.l1);
        Button l2 = dialogView.findViewById(R.id.l2);
        Button l3 = dialogView.findViewById(R.id.l3);
        Button l4 = dialogView.findViewById(R.id.l4);
        Button l5 = dialogView.findViewById(R.id.l5);
        Button l6 = dialogView.findViewById(R.id.l6);
        Button l7 = dialogView.findViewById(R.id.l7);
        Button l8 = dialogView.findViewById(R.id.l8);
        Button l9 = dialogView.findViewById(R.id.l9);
        Button l10 = dialogView.findViewById(R.id.l10);
        Button l11 = dialogView.findViewById(R.id.l11);
        Button l12 = dialogView.findViewById(R.id.l12);
        Button l13 = dialogView.findViewById(R.id.l13);
        Button l14 = dialogView.findViewById(R.id.l14);
        Button l15 = dialogView.findViewById(R.id.l15);
        Button l16 = dialogView.findViewById(R.id.l16);
        Button l17 = dialogView.findViewById(R.id.l17);
        Button l18 = dialogView.findViewById(R.id.l18);
        Button l19 = dialogView.findViewById(R.id.l19);
        Button l20 = dialogView.findViewById(R.id.l20);
        Button l21 = dialogView.findViewById(R.id.l21);
        Button l22 = dialogView.findViewById(R.id.l22);
        Button l23 = dialogView.findViewById(R.id.l23);
        Button l24 = dialogView.findViewById(R.id.l24);
        Button l25 = dialogView.findViewById(R.id.l25);

        final TextView selectLocal = (TextView) findViewById(R.id.selectLocal);
        final AlertDialog dialog = builder.create();
        dialog.show();

        l1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("강남구");
                dialog.cancel();
            }
        });
        l2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("강동구");
                dialog.cancel();
            }
        });
        l3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("강북구");
                dialog.cancel();
            }
        });
        l4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("강서구");
                dialog.cancel();
            }
        });
        l5.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("관악구");
                dialog.cancel();
            }
        });
        l6.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("광진구");
                dialog.cancel();
            }
        });
        l7.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("구로구");
                dialog.cancel();
            }
        });
        l8.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("금천구");
                dialog.cancel();
            }
        });
        l9.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("노원구");
                dialog.cancel();
            }
        });
        l10.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("도봉구");
                dialog.cancel();
            }
        });
        l11.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("동대문구");
                dialog.cancel();
            }
        });
        l12.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("동작구");
                dialog.cancel();
            }
        });
        l13.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("마포구");
                dialog.cancel();
            }
        });
        l14.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("서대문구");
                dialog.cancel();
            }
        });
        l15.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("서초구");
                dialog.cancel();
            }
        });
        l16.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("성동구");
                dialog.cancel();
            }
        });
        l17.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("성북구");
                dialog.cancel();
            }
        });
        l18.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("송파구");
                dialog.cancel();
            }
        });
        l19.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("양천구");
                dialog.cancel();
            }
        });
        l20.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("영등포구");
                dialog.cancel();
            }
        });
        l21.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("용산구");
                dialog.cancel();
            }
        });
        l22.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("은평구");
                dialog.cancel();
            }
        });
        l23.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("종로구");
                dialog.cancel();
            }
        });
        l24.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("중구");
                dialog.cancel();
            }
        });
        l25.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectLocal.setText("중랑구");
                dialog.cancel();
            }
        });
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

