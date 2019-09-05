package org.techtown.myaroundme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity{
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int REQUEST_TAKE_ALBUM = 222;
    private static final int REQUEST_IMAGE_CROP = 333;
    private Button selectBoard = null;
    private Button selectLocal = null;
    private Button selectCategory = null;

    private CheckBox anonymous;
    private String imageFilePath;
    private Uri photoUri, albumUri;
    private Button home,board,map,alarm;
    private ImageButton backButton, upload;
    private TextView content;
    private EditText title;

    private Bitmap tmp;

    static int count = 0;

    public WriteActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        selectBoard = findViewById(R.id.selectBoard);
        selectLocal = findViewById(R.id.selectLocal);
        selectCategory = findViewById(R.id.selectCategory);

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);
        backButton=(ImageButton)findViewById(R.id.backButton);
        upload=(ImageButton)findViewById(R.id.upload);
        content = findViewById(R.id.content);
        title = findViewById(R.id.title);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                count = 0;
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(WriteActivity.this,MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(WriteActivity.this,BoardActivity.class);
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
                Intent alarmIntent = new Intent(WriteActivity.this,NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WriteActivity.JSONTask().execute("http://192.168.0.168:3000/process/addpost"); //AsyncTask 시작시킴
            }
        });
    }

    public void OnBoardPopupClick (View v){
        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.activity_select4, null);
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setView(dialogView);

        Button board1 = dialogView.findViewById(R.id.board1);
        Button board2 = dialogView.findViewById(R.id.board2);

        final AlertDialog dialog = builder.create();
        dialog.show();

        board1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectBoard.setText("자유게시판");
                dialog.cancel();
            }
        });

        board2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectBoard.setText("지역게시판");
                dialog.cancel();
            }
        });
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

    public void OnPopupClick (View v){
        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.activity_photopoup, null);
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setView(dialogView);

        Button select_album = dialogView.findViewById(R.id.album);
        Button take_photo = dialogView.findViewById(R.id.takephoto);

        final AlertDialog dialog = builder.create();
        dialog.show();

        select_album.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectGallery();
                dialog.cancel();
            }
        });
        take_photo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                sendTakePhotoIntent();
                dialog.cancel();
            }
        });
    }

    public void OnCategoryPopupClick (View v){
        Button board = findViewById(R.id.selectBoard);

        if(board.getText() == "지역게시판"){
            LayoutInflater inflater=getLayoutInflater();

            final View dialogView= inflater.inflate(R.layout.activity_select, null);
            final AlertDialog.Builder builder= new AlertDialog.Builder(this);

            builder.setView(dialogView);

            Button hobby = dialogView.findViewById(R.id.s1);
            Button club = dialogView.findViewById(R.id.s2);

            final AlertDialog dialog = builder.create();
            dialog.show();

            hobby.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    selectCategory.setText("취미");
                    dialog.cancel();
                }
            });

            club.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    selectCategory.setText("동호회");
                    dialog.cancel();
                }
            });

        }

        else if(board.getText() == "자유게시판"){
            LayoutInflater inflater=getLayoutInflater();

            final View dialogView= inflater.inflate(R.layout.activity_select2, null);
            final AlertDialog.Builder builder= new AlertDialog.Builder(this);

            builder.setView(dialogView);

            Button free = dialogView.findViewById(R.id.s3);
            Button promote = dialogView.findViewById(R.id.s4);
            Button counsel = dialogView.findViewById(R.id.s5);

            final AlertDialog dialog = builder.create();
            dialog.show();

            free.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    selectCategory.setText("자유");
                    dialog.cancel();
                }
            });

            promote.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    selectCategory.setText("홍보");
                    dialog.cancel();
                }
            });

            counsel.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    selectCategory.setText("고민상담");
                    dialog.cancel();
                }
            });

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //사진 촬영하기
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            count++;

            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            } else {
                exifDegree = 0;
            }

            if(count == 1)
                ((ImageView)findViewById(R.id.photo1)).setImageBitmap(rotate(bitmap, exifDegree));

            else if(count == 2)
                ((ImageView)findViewById(R.id.photo2)).setImageBitmap(rotate(bitmap, exifDegree));

            else if(count == 3)
                ((ImageView)findViewById(R.id.photo3)).setImageBitmap(rotate(bitmap, exifDegree));
        }
        // 앨범에서 사진 가져오기
        else if (requestCode == REQUEST_TAKE_ALBUM && resultCode == RESULT_OK){
            if(data.getData ()!= null){
                try{
                    File albumfile = null;
                    albumfile = createImageFile();
                    photoUri = data.getData();
                    albumUri = Uri.fromFile(albumfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            count++;
            cropImage();
        }
        // 선택한 사진 수정하기
        else if (requestCode == REQUEST_IMAGE_CROP && resultCode == RESULT_OK){
            galleryAddPick();
            if(count == 1)
                ((ImageView)findViewById(R.id.photo1)).setImageURI(albumUri);

            else if(count == 2)
                ((ImageView)findViewById(R.id.photo2)).setImageURI(albumUri);

            else if(count == 3)
                ((ImageView)findViewById(R.id.photo3)).setImageURI(albumUri);
        }

    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        tmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void sendTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void galleryAddPick(){
        Intent mediScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imageFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediScanIntent.setData(contentUri);
        sendBroadcast(mediScanIntent);
    }

    public void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoUri,"image/*");
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumUri);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }
    //string을 bitmap이미지로 역변환
    private Bitmap getBitmapFromString(String jsonString) {
        /*
         * This Function converts the String back to Bitmap
         * */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            anonymous = (CheckBox) findViewById(R.id.anonymous);
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("title", String.valueOf(title.getText()));
                jsonObject.accumulate("content", String.valueOf(content.getText()));
                //로그인 정보 확인
                if(LoginActivity.loginuser.getId() == null) {
                    Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginIntent);
                }

                else
                    jsonObject.accumulate("writer", String.valueOf(LoginActivity.loginuser.id));
                //지역 추가
                if(selectLocal.getText().equals("지역"))
                    jsonObject.accumulate("area", "");
                else
                    jsonObject.accumulate("area", String.valueOf(selectLocal.getText()));
                if (anonymous.isChecked())
                    jsonObject.accumulate("anonymous", 1);
                else
                    jsonObject.accumulate("anonymous", 0);


                Button board = findViewById(R.id.selectBoard);
                Button category = findViewById(R.id.selectCategory);

                if(board.getText() == "자유게시판" && category.getText() == "자유"){
                    jsonObject.accumulate("type", String.valueOf("free"));
                }
                else if(board.getText() == "자유게시판" && category.getText() == "고민상담"){
                    jsonObject.accumulate("type", "worryposts");
                }
                else if(board.getText() == "자유게시판" && category.getText() == "홍보"){
                    jsonObject.accumulate("type", "promotion");
                }
                else if(board.getText() == "지역게시판" && category.getText() == "취미"){
                    jsonObject.accumulate("type", "hobby");
                }
                else if(board.getText() == "지역게시판" && category.getText() == "동호회"){
                    jsonObject.accumulate("type", "club");
                }

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

            if (result.equals("Upload Fail")) {
                Toast.makeText(getApplicationContext(),
                        "업로드 실패", Toast.LENGTH_LONG).show();
            }

            else {
                Toast.makeText(getApplicationContext(),
                        "업로드 성공", Toast.LENGTH_LONG).show();
            }
        }
    }
}