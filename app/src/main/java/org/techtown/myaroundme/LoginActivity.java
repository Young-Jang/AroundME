package org.techtown.myaroundme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    static boolean isLogin;
    static LoginUser loginuser;

    private Button login,join,findAccount;
    private EditText id, password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnLogin);
        join = findViewById(R.id.btnJoin);
        findAccount = findViewById(R.id.btnFind);
        id = findViewById(R.id.id);
        password = findViewById(R.id.password);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(joinIntent);
            }
        });

        findAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findIntent = new Intent(LoginActivity.this, FindAccountActivity.class);
                startActivity(findIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginActivity.JSONTask().execute("http://192.168.0.168:3000/login"); //AsyncTask 시작시킴
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", String.valueOf(id.getText()));
                jsonObject.accumulate("password", String.valueOf(password.getText()));

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
            String [] a;
            if(result.equals("Login Fail1")){
                Toast.makeText(getApplicationContext(),
                        "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                id.setText("");
                password.setText("");
            }
            else if(result.equals("Login Fail2")){
                Toast.makeText(getApplicationContext(),
                        "존재하지 않는 아이디 입니다.", Toast.LENGTH_LONG).show();
                id.setText("");
                password.setText("");
            }
            else{
                try {
                    loginuser = new LoginUser();
                    JSONObject jObject = new JSONObject(result);
                    loginuser.setId(jObject.getString("id"));
                    loginuser.setPassword(jObject.getString("hashed_password"));
                    loginuser.setName(jObject.getString("name"));
                    loginuser.setSex(jObject.getString("sex"));
                    loginuser.setBirth(jObject.getString("birth"));
                    loginuser.setEmail(jObject.getString("email"));
                    loginuser.setPhone(jObject.getString("phone"));

                    Toast.makeText(getApplicationContext(),
                            "로그인 완료.", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                isLogin = true;
                Intent authenticateIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(authenticateIntent);
            }
        }
    }
}
