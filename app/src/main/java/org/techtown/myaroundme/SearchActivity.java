package org.techtown.myaroundme;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    private ImageButton backButton;
    EditText searchText;
    ImageButton check;

    private ListView listView;
    private ListViewAdapter adapter;

    ArrayList<ListViewItem> oData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchText = (EditText) findViewById(R.id.search);
        check = findViewById(R.id.ok);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SearchActivity.JSONTask().execute("http://192.168.0.168:3000/search"); //AsyncTask 시작시킴
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("search", String.valueOf(searchText.getText()));

                BufferedReader reader = null;
                HttpURLConnection con = null;

                try {
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

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }

                    try {
                        if (reader != null) {
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            oData = new ArrayList<>();


            if (result.equals("Search Fail")) {
                //오류 처리
            }

            else {

                JSONArray jarray = null;   // JSONArray 생성

                try {
                    jarray = new JSONArray(result);

                    for(int i=0; i < jarray.length(); i++){
                        JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                        String title = jObject.getString("title");
                        String content = jObject.getString("content");
                        String writer = jObject.getString("writer");
                        int star = jObject.getInt("star");
                        int comment = jObject.getInt("commentcount");
                        String key = jObject.getString("_id");

                        ListViewItem oItem = new ListViewItem();

                        oItem.strTitle = title;
                        oItem.strDate = content;
                        oItem.like = String.valueOf(star);
                        oItem.comment = String.valueOf(comment);
                        oItem.nickname =  writer;
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
                        Intent postIntent = new Intent(SearchActivity.this,PostActivity.class);
                        postIntent.putExtra("w", oData.get(position).key);
                        startActivity(postIntent);
                    }
                });
            }
        }
    }
}