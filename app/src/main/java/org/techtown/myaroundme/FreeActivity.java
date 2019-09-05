package org.techtown.myaroundme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import java.util.List;

public class FreeActivity extends AppCompatActivity implements View.OnClickListener {
    private final int MAX_POST = 1000;

    private Button home,board,map,alarm,check,posting;
    private ImageButton searchButton,backButton;
    private Animation fab_open,fab_close;
    private Boolean isFabOpen=false;
    private FloatingActionButton fab, fab1,fab2,fab3;
    private ListView listView;
    private ListViewAdapter adapter;
    private int count=0;

    private String category = null;

    String title[]={"심심해요","요즘 아이스크림 원래 이렇게 하나요","급 추위..."};
    String content[]={"놀아주실분....","알뜰슈퍼 갔는데 아이스크림 바 하나에 천원씩하네요...","어제 문열어넣고 잤더니 감기걸림ㅠㅠ"};
    String nickname[]={"상도백수","상도맘","익명"};
    String comment[]={"0","0","0"};
    String like[]={"5","15","5"};
    int pic[]={R.drawable.person,R.drawable.person,R.drawable.person};

    ArrayList<ListViewItem> oData;

    ImageView gray;
    TextView fabText1,fabText2,fabText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);
        check = (Button)findViewById(R.id.check);



        searchButton=(ImageButton)findViewById(R.id.searchButton);
        backButton=(ImageButton)findViewById(R.id.backButton);

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
                Intent homeIntent = new Intent(FreeActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(FreeActivity.this,BoardActivity.class);
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
                Intent alarmIntent = new Intent(FreeActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
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
                Intent postIntent = new Intent(FreeActivity.this,PostActivity.class);
                postIntent.putExtra("title", oData.get(position).strTitle);
                postIntent.putExtra("content",oData.get(position).strDate);
                postIntent.putExtra("nickname",oData.get(position).nickname);
                postIntent.putExtra("comment",oData.get(position).comment);
                postIntent.putExtra("like",oData.get(position).like);
                postIntent.putExtra("photo",oData.get(position).PostPhoto);
                startActivity(postIntent);
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
                Intent searchButtonIntent = new Intent(FreeActivity.this,SearchActivity.class);
                startActivity(searchButtonIntent);
            }
        });

        fab.setOnClickListener((View.OnClickListener) this);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(FreeActivity.this,WriteActivity.class);
                startActivity(writeIntent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(FreeActivity.this, PhotoActivity.class);
                reportIntent.putExtra("w", "report");
                startActivity(reportIntent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(FreeActivity.this,PhotoActivity.class);
                cameraIntent.putExtra("w", "camera");
                startActivity(cameraIntent);

            }
        });

        gray = (ImageView)findViewById(R.id.grayback);

        new FreeActivity.JSONTask().execute("http://192.168.0.168:3000/process/loadpost"); //AsyncTask 시작시킴
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

    public void OnPopupClick2 (View v){
        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.activity_select2, null);
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setView(dialogView);

        Button free = dialogView.findViewById(R.id.s3);
        Button promote = dialogView.findViewById(R.id.s4);
        Button counsel = dialogView.findViewById(R.id.s5);

        final Button select = findViewById(R.id.select);

        final AlertDialog dialog = builder.create();
        dialog.show();

        free.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                select.setText("자유");
                dialog.cancel();
            }
        });

        promote.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                select.setText("홍보");
                dialog.cancel();
            }
        });

        counsel.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                select.setText("고민상담");
                dialog.cancel();
            }
        });
    }

    public void OnCheckClick (View v){
        final Button select = findViewById(R.id.select);

        if(select.getText() == "자유")
            category = "free";
        else if(select.getText() == "홍보")
            category = "promotion";
        else if(select.getText() == "고민상담")
            category = "worry";

        new FreeActivity.JSONTask().execute("http://192.168.0.168:3000/process/loadcategory"); //AsyncTask 시작시킴
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("areagroup", 1);

                if(category != null)
                    jsonObject.accumulate("category", category);


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

            }

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
                       // oItem.like = String.valueOf(star);
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
                        Intent postIntent = new Intent(FreeActivity.this,PostActivity.class);
                        postIntent.putExtra("w", oData.get(position).key);
                        startActivity(postIntent);
                    }
                });
            }
        }
    }
}