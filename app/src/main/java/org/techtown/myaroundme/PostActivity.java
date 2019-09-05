package org.techtown.myaroundme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class PostActivity extends AppCompatActivity implements View.OnClickListener{
    private Button home,board,map,alarm;
    private ImageButton backButton, check,likebutton;
    private ListView listView;
    private CheckBox anonymous;

    private TextView selectboard, nickname, time, title, content, likecount, commentcount;
    private ImageView profile,photo;
    private EditText comment;
    private boolean iscomment = false;

    private String stlike;
    private int liked,commented;
    private String keyvalue, commentlist = null;

    ArrayList<CommentList> oData;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        backButton=(ImageButton)findViewById(R.id.backButton);
        likebutton=(ImageButton)findViewById(R.id.likeButton);

        anonymous=(CheckBox)findViewById(R.id.anonymous);

        check = findViewById(R.id.check);
        comment = findViewById(R.id.comment);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(PostActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(PostActivity.this,BoardActivity.class);
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
                Intent alarmIntent = new Intent(PostActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getText() != null) {
                    iscomment = true;
                    new PostActivity.JSONTask().execute("http://192.168.0.168:3000/comment"); //AsyncTask 시작시킴
                }
            }
        });

        selectboard = findViewById(R.id.selectBoard);
        nickname = findViewById(R.id.nickname);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        likecount = findViewById(R.id.likeCount);
        commentcount = findViewById(R.id.commentCount);
        profile = findViewById(R.id.profileImage);
        photo=(ImageView)findViewById(R.id.photo);

        Intent intent = getIntent();
        keyvalue = intent.getExtras().getString("w");
        nickname.setText(intent.getExtras().getString("nickname"));
        title.setText(intent.getExtras().getString("title"));
        content.setText(intent.getExtras().getString("content"));
        likecount.setText(intent.getExtras().getString("like"));
        commentcount.setText(intent.getExtras().getString("comment"));
        profile.setImageResource(intent.getExtras().getInt("photo"));
        photo.setImageResource(intent.getExtras().getInt("photos"));

        liked = Integer.parseInt(likecount.getText().toString());
        commented = Integer.parseInt(commentcount.getText().toString());

        likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liked++;
                likecount.setText(""+liked);
            }
        });
        oData = new ArrayList<>();
        listView=(ListView)findViewById(R.id.CommentList);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment.getText();
                commented++;
                commentcount.setText(""+commented);

                CommentList oItem = new CommentList();
                if(anonymous.isChecked())
                {
                    oItem.conickname="익명";
                }
                else
                    oItem.conickname = "소소";
                oItem.cocontent = comment.getText().toString();
                oData.add(oItem);
                if(oData != null){
                    CommentListAdapter oAdapter = new CommentListAdapter(oData);
                    listView.setAdapter(oAdapter);
                }
            }
        });

        new PostActivity.JSONTask().execute("http://192.168.0.168:3000/process/post"); //AsyncTask 시작시킴
    }

    @Override
    public void onClick(View v)
    {
    }
    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("_id", String.valueOf(keyvalue));

                if(iscomment) {
                    jsonObject.accumulate("content", String.valueOf(comment.getText()));
                    jsonObject.accumulate("writer", LoginActivity.loginuser.getId());
                    iscomment = false;
                }

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
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

            }

            else if(result.equals("Upload Success")){
                Toast.makeText(getApplicationContext(),"댓글 등록 성공",Toast.LENGTH_LONG).show();
            }

            else{

                try {
                    JSONArray jarray = new JSONArray(result);

                    for(int i=0; i < jarray.length(); i++){
                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출

                        title.setText(jObject.getString("title"));
                        content.setText(jObject.getString("content"));
                        if(jObject.getString("anonymous").equals("1")) {
                            nickname.setText("익명");
                        }
                        else{
                            nickname.setText(jObject.getString("writer"));
                        }
                        time.setText(jObject.getString("created_at"));
                        likecount.setText(String.valueOf(jObject.getInt("star")));
                        commentcount.setText(String.valueOf(jObject.getInt("commentcount")));
                        commentlist = jObject.getString("comments");
                    }


                    JSONArray jarray2 = new JSONArray(commentlist);

                    for (int i = 0; i < jarray2.length(); i++) {
                        JSONObject jObject = jarray2.getJSONObject(i);  // JSONObject 추출

                        String content = jObject.getString("content");
                        String writer = jObject.getString("writer");

                        CommentList oItem = new CommentList();

                        oItem.conickname = writer;
                        oItem.cocontent = content;
                        oData.add(oItem); }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listView=(ListView)findViewById(R.id.CommentList);

                if(oData != null){
                    CommentListAdapter oAdapter = new CommentListAdapter(oData);
                    listView.setAdapter(oAdapter);
                }
            }
        }
    }
}
