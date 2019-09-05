package org.techtown.myaroundme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class BoardActivity extends AppCompatActivity implements View.OnClickListener{
    private Button home, board, map, alarm;
    private ImageButton hotButton, freeButton, localButton, searchButton, reportButton;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;

    private ImageSwitcher is1;
    private TextSwitcher ts1;
    int index;
    float downX,upX;
    String texts[]={"흑석역 앞 중앙 사우나에 불났어요","상도역 사거리 교통사고","보라매 아파트 추락사고발생"};
    int images[]={R.drawable.fire,R.drawable.car_accident,R.drawable.fall};



    ImageView gray;
    TextView fabText1,fabText2,fabText3;
    ImageView star1, star2, star3, star4;

    boolean isChecked[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        is1=(ImageSwitcher)findViewById(R.id.is1);
        is1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });
        ts1=(TextSwitcher)findViewById(R.id.ts1);
        ts1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setTextSize(20.f);
                textView.setTextColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });


        is1.setImageResource(images[index]);
        ts1.setText(texts[index]);


        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        hotButton = (ImageButton)findViewById(R.id.hotButton);
        freeButton = (ImageButton)findViewById(R.id.freeButton);
        localButton = (ImageButton)findViewById(R.id.localButton);
        searchButton = (ImageButton)findViewById(R.id.searchButton);
        reportButton = (ImageButton)findViewById(R.id.reportButton);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3=(FloatingActionButton)findViewById(R.id.fab3);

        fabText1 = (TextView)findViewById(R.id.fabtext1);
        fabText2 = (TextView)findViewById(R.id.fabtext2);
        fabText3 = (TextView)findViewById(R.id.fabtext3);

        hotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotIntent = new Intent(BoardActivity.this,HotActivity.class);
                startActivity(hotIntent);
            }
        });

        freeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent freeIntent = new Intent(BoardActivity.this,FreeActivity.class);
                startActivity(freeIntent);
            }
        });

        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = new Intent(BoardActivity.this,LocalActivity.class);
                startActivity(localIntent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(BoardActivity.this,ReportBoardActivity.class);
                startActivity(reportIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(BoardActivity.this,SearchActivity.class);
                startActivity(searchIntent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(BoardActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(BoardActivity.this,BoardActivity.class);
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
                Intent alarmIntent = new Intent(BoardActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });

        fab.setOnClickListener((View.OnClickListener) this);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(BoardActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(BoardActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(BoardActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        gray = (ImageView)findViewById(R.id.grayback);

        isChecked = new boolean[4];

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);

        star1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[0]){
                    star1.setImageResource(R.drawable.board_selected);
                    isChecked[0] = true;
                }

                else{
                    star1.setImageResource(R.drawable.board_unselected);
                    isChecked[0] = false;
                }
            }
        });

        star2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[1]){
                    star2.setImageResource(R.drawable.board_selected);
                    isChecked[1] = true;
                }

                else{
                    star2.setImageResource(R.drawable.board_unselected);
                    isChecked[1] = false;
                }
            }
        });

        star3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[2]){
                    star3.setImageResource(R.drawable.board_selected);
                    isChecked[2] = true;
                }

                else{
                    star3.setImageResource(R.drawable.board_unselected);
                    isChecked[2] = false;
                }
            }
        });

        star4.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(!isChecked[3]){
                    star4.setImageResource(R.drawable.board_selected);
                    isChecked[3] = true;
                }

                else{
                    star4.setImageResource(R.drawable.board_unselected);
                    isChecked[3] = false;
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            downX=event.getX();
        }
        else if(event.getAction()==MotionEvent.ACTION_UP){
            upX=event.getX();

            if(downX<upX){
                index--;
                is1.setInAnimation(this,android.R.anim.slide_in_left);
                is1.setOutAnimation(this,android.R.anim.slide_out_right);
                if(index<0)
                    index=images.length-1;
                is1.setImageResource(images[index]);
                ts1.setText(texts[index]);
            }
            else if(downX>upX)
                {
                    index++;
                    is1.setInAnimation(this,R.anim.slide_in_right);
                    is1.setOutAnimation(this,R.anim.slide_out_left);
                    if(index==images.length)
                        index=0;
                    is1.setImageResource(images[index]);
                    ts1.setText(texts[index]);
                }
        }
        return super.onTouchEvent(event);
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