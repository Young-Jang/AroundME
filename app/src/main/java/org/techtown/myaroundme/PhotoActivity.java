package org.techtown.myaroundme;

import android.annotation.SuppressLint;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class PhotoActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 111;
    private static final int REQUEST_TAKE_ALBUM = 222;
    private static final int REQUEST_IMAGE_CROP = 333;

    private String imageFilePath;
    private Uri photoUri, albumUri;
    private EditText report;
    private ImageButton gps, check;
    private MyLocation mylocation;
    private Button home,board,map,alarm;
    private ImageButton backButton;
    private EditText title, location, content;


    static int count = 0;
    String type;


    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        type = intent.getExtras().getString("w");

        if(type.equals("camera"))
            sendTakePhotoIntent();

        mylocation = new MyLocation(this);

        gps = findViewById(R.id.gps2);
        report = findViewById(R.id.mylocation);
        title = findViewById(R.id.title);
        location = findViewById(R.id.mylocation);
        content = findViewById(R.id.content);
        check = findViewById(R.id.next);

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report.setText(mylocation.getAddress(PhotoActivity.this, mylocation.getLatitude(), mylocation.getLongitude()));
            }
        });

        home = (Button)findViewById(R.id.home);
        board = (Button)findViewById(R.id.board);
        map = (Button)findViewById(R.id.map);
        alarm = (Button)findViewById(R.id.alarm);

        backButton=(ImageButton)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;
                onBackPressed();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(PhotoActivity.this, MainActivity.class);
                startActivity(homeIntent);
            }
        });
        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardIntent = new Intent(PhotoActivity.this, BoardActivity.class);
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
                Intent alarmIntent = new Intent(PhotoActivity.this, NotificationActivity.class);
                startActivity(alarmIntent);
            }
        });
    }
    //사진 촬영하기
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
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

                jsonObject.accumulate("area", String.valueOf(location.getText()));
                jsonObject.accumulate("type", "report");

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
