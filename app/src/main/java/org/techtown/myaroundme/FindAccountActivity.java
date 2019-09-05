package org.techtown.myaroundme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class FindAccountActivity extends AppCompatActivity {
    private Button login, check;
    private EditText name, email, firstnumber, middlenumber, lastnumber, year, month, day;
    private TextView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findaccount);

        login = findViewById(R.id.btnLogin);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        firstnumber = findViewById(R.id.phonefirstr);
        middlenumber = findViewById(R.id.phonemiddle);
        lastnumber = findViewById(R.id.phonelast);
        year = findViewById(R.id.year);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        check = findViewById(R.id.next);
        account = findViewById(R.id.result);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FindAccountActivity.JSONTask().execute("http://192.168.0.168:3000/findid"); //AsyncTask 시작시킴
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(FindAccountActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", String.valueOf(name.getText()));
                jsonObject.accumulate("year", String.valueOf(year.getText()));
                jsonObject.accumulate("month", String.valueOf(month.getText()));
                jsonObject.accumulate("day", String.valueOf(day.getText()));
                jsonObject.accumulate("email", String.valueOf(email.getText()));
                jsonObject.accumulate("phonefirst", String.valueOf(firstnumber.getText()));
                jsonObject.accumulate("phonemiddle", String.valueOf(middlenumber.getText()));
                jsonObject.accumulate("phonelast", String.valueOf(lastnumber.getText()));

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

            if(result.equals("Find Fail")){
                account.setText("입력정보를 다시 확인해주세요.");
            }

            else{
                account.setText("아이디는 " + result + " 입니다.");
            }
        }
    }

}