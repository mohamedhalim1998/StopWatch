package com.mohamed.halim.essa.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    // get the time shen pause the timer or change state
    long timePaused;
    // indicate if the timer is running
    boolean running;
    // Button to start the timer
    Button start;
    // button to pause the timer
    Button pause;
    // button to stop the timer
    Button stop;

    // show the timer
    Chronometer timer;

    // save the state of the app
    Bundle instanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the view for the start button
        start = findViewById(R.id.start);
        // get the view for the pause button
        pause = findViewById(R.id.pause);
        // get the view for the stop button
        stop = findViewById(R.id.stop);
        // get the view for the Chronometer
        timer = findViewById(R.id.timer_text_view);

        // set event onclick for start button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start thr timer if not running
                if (!running) {
                    startTimer();
                    running = true;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // stop the timer if running
                if (running) {
                    timer.stop();
                    timePaused = 0;
                    running = false;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pause the timer if running
                if (running) {
                    // save the current time
                    timePaused = SystemClock.elapsedRealtime() - timer.getBase();
                    // stop the timer
                    timer.stop();
                    running = false;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get the state if not null
        if (instanceState != null)
            onRestoreInstanceState(instanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save the state of the app
        instanceState = new Bundle();
        onSaveInstanceState(instanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the current
        outState.putLong("timePaused", timePaused);
        // save the current running state
        outState.putBoolean("running", running);

        outState.putString("text",timer.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // restore the time
        timePaused = savedInstanceState.getLong("timePaused");
        // restore running state
        running = savedInstanceState.getBoolean("running");

        if (running) {
            startTimer();
        } else {
            timer.setText(savedInstanceState.getString("text"));
        }
    }

    /**
     * start the timer
     */
    private void startTimer() {
        timer.setBase(SystemClock.elapsedRealtime() - timePaused);
        timer.start();
    }

}
