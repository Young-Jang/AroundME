package org.techtown.myaroundme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class AuthenticateActivity extends AppCompatActivity {
    private Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(AuthenticateActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    public void OnCheckClick (View v){
        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.activity_joinpopup, null);
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setView(dialogView);

        Button main = dialogView.findViewById(R.id.main);
        Button login = dialogView.findViewById(R.id.login);

        final Button select = findViewById(R.id.select);
        final AlertDialog dialog = builder.create();
        dialog.show();

        main.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
                Intent mainIntent = new Intent(AuthenticateActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
                Intent loginIntent = new Intent(AuthenticateActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}

