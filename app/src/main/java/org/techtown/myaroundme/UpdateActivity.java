package org.techtown.myaroundme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class UpdateActivity extends AppCompatActivity {
    private Button selectLocal, update;
    private EditText password, nickname, firstnumber, middlenumber, lastnumber;
    private TextView id, name, email, year, month, day;
    private RadioButton male, female;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id = findViewById(R.id.updateid);
        password = findViewById(R.id.updatepassword);
        name = findViewById(R.id.updatename);
        email = findViewById(R.id.updateemail);
        firstnumber = findViewById(R.id.updatephonefirst);
        middlenumber = findViewById(R.id.updatephonemiddle);
        lastnumber = findViewById(R.id.updatephonelast);
        year = findViewById(R.id.updateyear);
        month = findViewById(R.id.updatemonth);
        day = findViewById(R.id.updateday);
        male = findViewById(R.id.updatemale);
        female = findViewById(R.id.updatefemale);
        selectLocal = findViewById(R.id.updateaddress);
        nickname = findViewById(R.id.updatenickname);
        update = findViewById(R.id.update);

        OnSetting();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateActivity.JSONTask().execute("http://192.168.0.168:3000/updateuser"); //AsyncTask 시작시킴
            }
        });
    }

    public void OnSetting () {
        id.setText(LoginActivity.loginuser.getId());
        password.setHint(LoginActivity.loginuser.getPassword());
        name.setText(LoginActivity.loginuser.getName());
        email.setText(LoginActivity.loginuser.getEmail());
        firstnumber.setHint(LoginActivity.loginuser.getPhone().substring(0,3));
        middlenumber.setHint(LoginActivity.loginuser.getPhone().substring(3,7));
        lastnumber.setHint(LoginActivity.loginuser.getPhone().substring(7,11));
        year.setText(LoginActivity.loginuser.getBirth().substring(0,4));
        month.setText(LoginActivity.loginuser.getBirth().substring(4,5));
        day.setText(LoginActivity.loginuser.getBirth().substring(5,6));

        if(LoginActivity.loginuser.getSex().equals("male"))
            male.setChecked(true);
        else
            female.setChecked(true);
        //address
        //nickname
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("password", String.valueOf(password.getText()));
                jsonObject.accumulate("phonefirst", String.valueOf(firstnumber.getText()));
                jsonObject.accumulate("phonemiddle", String.valueOf(middlenumber.getText()));
                jsonObject.accumulate("phonelast", String.valueOf(lastnumber.getText()));
                jsonObject.accumulate("nickname", String.valueOf(nickname.getText()));
                //jsonObject.accumulate("address", String.valueOf(selectLocal.getText()));

                if(male.isChecked())
                    jsonObject.accumulate("sex", "male");
                else if(female.isChecked())
                    jsonObject.accumulate("sex", "female");

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

            if(result.equals("Update Success")){
                Toast.makeText(getApplicationContext(), "업데이트 성공", Toast.LENGTH_LONG).show();
            }

            else if(result.equals("Update Fail")){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UpdateActivity.this);
                alertDialogBuilder.setTitle("개인 정보 변경 오류");

                alertDialogBuilder
                        .setMessage("입력 정보를 다시 확인해 주세요")
                        .setCancelable(false)
                        .setNegativeButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
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

        //final Button selectLocal = findViewById(R.id.address);
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
}
