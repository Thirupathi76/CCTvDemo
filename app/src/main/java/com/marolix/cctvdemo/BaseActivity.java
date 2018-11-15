package com.marolix.cctvdemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        findViewById(R.id.search_vid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection(BaseActivity.this))
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
                else
                    Toast.makeText(BaseActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.view_vid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkConnection(BaseActivity.this))
                    startActivity(new Intent(BaseActivity.this, ViewVidActivity.class));
                else
                    Toast.makeText(BaseActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static boolean checkConnection(Context context) {
      /*  ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
            }
        } else {
            // not connected to the internet
        }*/

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
