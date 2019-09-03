package com.mohamed.halim.essa.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    long seconds = 0;

    Button start;
    Button pause;
    Button stop;

    TextView timer;

    boolean stopTime = false;
    boolean running = false;

    Handler mHandler;
    Runnable runnable;

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);

        timer = findViewById(R.id.timer_text_view);
        timer.setText(getTime(seconds));

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running) {

                    startTimer();
                    running = true;
                }
            }

        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seconds = 0;
                running = false;
                timer.setText(getTime(seconds));
                if(runnable != null) {
                    mHandler.removeCallbacks(runnable);
                }

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTime = true;
                running = false;
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(savedInstanceState != null)
        onRestoreInstanceState(savedInstanceState);
    }

    private void startTimer(){
        stopTime = false;
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (!stopTime) {
                    mHandler.postDelayed(this, 900);
                    seconds ++;
                    timer.setText(getTime(seconds));
                }
            }

        };
        mHandler.postDelayed(runnable,900);
    }


    private String getTime(long seconds) {
        int hours = (int) (seconds / 60 / 60);
        int minutes = (int) (seconds/ 60 % 60);
        seconds = (int) (seconds % 60);
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savedInstanceState = new Bundle();
        onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(runnable != null)
            mHandler.removeCallbacks(runnable);
        outState.putLong("seconds", seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("stopTime",stopTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        seconds = savedInstanceState.getLong("seconds");
        running = savedInstanceState.getBoolean("running");
        stopTime = savedInstanceState.getBoolean("stopTime");
        timer.setText(getTime(seconds));
        if(running) {
            startTimer();
        }
    }
}
