package com.mohamed.halim.essa.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    long timePaused;

    Button start;
    Button pause;
    Button stop;

    Chronometer timer;

    Bundle instanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);

        timer = findViewById(R.id.timer_text_view);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.stop();
                timePaused = 0;
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePaused = SystemClock.elapsedRealtime() - timer.getBase();
                timer.stop();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(instanceState != null) {
            onRestoreInstanceState(instanceState);
            startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        timePaused = SystemClock.elapsedRealtime() - timer.getBase();
        instanceState = new Bundle();
        onSaveInstanceState(instanceState);
        timer.stop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timePaused",timePaused);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timePaused = savedInstanceState.getLong("timePaused");
        startTimer();
    }

    private void startTimer() {
        timer.setBase(SystemClock.elapsedRealtime() - timePaused);
        timer.start();
    }

}
