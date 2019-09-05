package org.techtown.myaroundme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ReportBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private Button home,board,map,alarm;
    private ImageButton searchButton,backButton;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen=false;
    private FloatingActionButton fab, fab1,fab2,fab3;
    private ListView listView;
    private ListViewAdapter adapter;
    private int count=0;

    private String area = null;

    String title[]={"흑석역 앞 중앙사우나에 불났어요","상도역 사거리 교통사고","보라매 아파트 추락사고 발생"};
    String content[]={"흑석역 앞에 중앙사우나에 불났어요!! 가족중에 중앙사우나 가신분들 얼른 연락해보세요! ","상도역 소소치킨앞 사거리에 교통사고나서 통제중이네요 참고하세요!","보라매 아파트 공사현장에서 추락사고 발생했어요. 지나갈때 우회해서 가시길ㅠㅠ"};
    String nickname[]={"찜질매니아","빵빵","지킴이"};
    String comment[]={"0","0","0"};
    String like[]={"25","17","11"};
    int pic[]={R.drawable.person,R.drawable.person,R.drawable.person};
    int photo[]={R.drawable.fire,R.drawable.car_accident,R.drawable.fall};

    ArrayList<ListViewItem> oData;

    ImageView gray;
    TextView fabText1,fabText2,fabText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_board);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        searchButton = (ImageButton)findViewById(R.id.searchButton);
        backButton = (ImageButton)findViewById(R.id.backButton);

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
                Intent homeIntent = new Intent(ReportBoardActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(ReportBoardActivity.this,BoardActivity.class);
                startActivity(boardIntent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(ReportBoardActivity.this,MapsActivity.class);
                startActivity(mapIntent);
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmIntent = new Intent(ReportBoardActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchButtonIntent = new Intent(ReportBoardActivity.this,SearchActivity.class);
                startActivity(searchButtonIntent);
            }
        });

        oData = new ArrayList<>();
        listView=(ListView)findViewById(R.id.listview);

        while(count<3) {
            ListViewItem setItem = new ListViewItem();

            setItem.strTitle = title[count];
            setItem.strDate = content[count];
            setItem.nickname = nickname[count];
            setItem.comment = comment[count];
            setItem.like = like[count];
            setItem.PostPhoto = pic[count];
            setItem.Photo=photo[count];
            oData.add(setItem);
            ListViewAdapter oAdapter = new ListViewAdapter(oData);
            listView.setAdapter(oAdapter);
            count++;
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position이 클릭된 위치입니다.
                // 컬렉션에서 적절하게 꺼내서 사용하시면 됩니다.
                Intent postIntent = new Intent(ReportBoardActivity.this,PostActivity.class);
                postIntent.putExtra("title", oData.get(position).strTitle);
                postIntent.putExtra("content",oData.get(position).strDate);
                postIntent.putExtra("nickname",oData.get(position).nickname);
                postIntent.putExtra("comment",oData.get(position).comment);
                postIntent.putExtra("like",oData.get(position).like);
                postIntent.putExtra("photo",oData.get(position).PostPhoto);
                postIntent.putExtra("photos",oData.get(position).Photo);
                startActivity(postIntent);
            }
        });


        fab.setOnClickListener((View.OnClickListener) this);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(ReportBoardActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(ReportBoardActivity.this,PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(ReportBoardActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);
            }
        });

        gray = (ImageView)findViewById(R.id.grayback);

        new ReportBoardActivity.JSONTask().execute("http://192.168.0.168:3000/process/loadpost"); //AsyncTask 시작시킴

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

        final Button selectLocal = findViewById(R.id.selectLocal);
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

    public void OnCheckClick (View v){
        final Button select = findViewById(R.id.selectLocal);
        area = String.valueOf(select.getText());

        new ReportBoardActivity.JSONTask().execute("http://192.168.0.168:3000/process/loadlocal"); //AsyncTask 시작시킴
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
    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("areagroup", 10);

                if(area != null)
                    jsonObject.accumulate("area", area);

                BufferedReader reader = null;
                HttpURLConnection con = null;

                try{
                    URL url = new URL(strings[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌
                    //서버로 부터 데이터를 받음 - 여기부터 안됨!!
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                }

                catch (MalformedURLException e){
                    e.printStackTrace();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                finally {
                    if(con != null){
                        con.disconnect();
                    }

                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            oData = new ArrayList<>();

            if(result.equals("Load Fail")){
                Toast.makeText(getApplicationContext(),
                        "불러오기 실패", Toast.LENGTH_LONG).show();            }

            else{
                JSONArray jarray = null;   // JSONArray 생성

                try {
                    jarray = new JSONArray(result);

                    for(int i=0; i < jarray.length(); i++){
                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        String title = jObject.getString("title");
                        String content = jObject.getString("content");
                        int star = jObject.getInt("star");
                        int comment = jObject.getInt("commentcount");
                        String key = jObject.getString("_id");

                        ListViewItem oItem = new ListViewItem();
                        if(jObject.getString("anonymous").equals("1")) {
                            String writer = "익명";
                            oItem.nickname =  writer;
                        }
                        else{
                            String writer = jObject.getString("writer");
                            oItem.nickname =  writer;
                        }
                        oItem.strTitle = title;
                        oItem.strDate = content;
                        oItem.like = String.valueOf(star);
                        oItem.comment = String.valueOf(comment);
                        oItem.key = key;
                        oData.add(oItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listView=(ListView)findViewById(R.id.listview);
                ListViewAdapter oAdapter = new ListViewAdapter(oData);
                listView.setAdapter(oAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // position이 클릭된 위치입니다.
                        // 컬렉션에서 적절하게 꺼내서 사용하시면 됩니다.
                        Intent postIntent = new Intent(ReportBoardActivity.this,PostActivity.class);
                        postIntent.putExtra("w", oData.get(position).key);
                        startActivity(postIntent);
                    }
                });
            }
        }
    }
}
