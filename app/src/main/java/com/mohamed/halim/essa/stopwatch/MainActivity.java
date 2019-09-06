package com.mohamed.halim.essa.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // reference to open stopwatch mode
        Button stopwatch = findViewById(R.id.stop_watch_mode);
        // reference to open countdown mode
        Button countdown = findViewById(R.id.count_down);

        stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,StopWatchActivity.class);
                startActivity(i);
            }
        });

        countdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CountDownActivity.class);
                startActivity(i);
            }
        });
    }
}
