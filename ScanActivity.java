package com.example.aleya.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by TiemLe on 11/16/17.
 */

public class ScanActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        Button user = (Button) findViewById(R.id.userbutton);
        user.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }

        });
        Button home = (Button) findViewById(R.id.homebutton);
        home.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), inventory.class));
            }

        });
    }
}
