package com.mohamed.halim.essa.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

public class CountDownActivity extends AppCompatActivity {

    // indicate if the timer is running
    private boolean running;

    // show the timer
    private Chronometer countDown;

    private EditText hoursEditText;
    private EditText minutesEditText;
    private EditText secondsEditText;
    long remainingSeconds = 0;
    long originalSeconds = 0;
    Bundle instanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        // get the view for the start button
        // Button to start the timer
        Button start = findViewById(R.id.start_count);
        // get the view for the pause button
        // button to pause the timer
        Button pause = findViewById(R.id.pause_count);
        // get the view for the stop button
        // button to stop the timer
        Button reset = findViewById(R.id.reset);

        // button to get set the counter time
        Button setTime = findViewById(R.id.set_time);
        // get the view for the Chronometer
        countDown = findViewById(R.id.counter_chronometer);
        // edit text for the hours
        hoursEditText = findViewById(R.id.time_hours);

        // edit text for the hours
        minutesEditText = findViewById(R.id.time_minutes);

        // edit text for the hours
        secondsEditText = findViewById(R.id.time_seconds);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTime();
            }
        });

        // set the initial timer
        countDown.setText(getTimeFormat(remainingSeconds));
        // set tick listener to update the timer
        countDown.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (remainingSeconds > 1) {
                    remainingSeconds--;
                } else {
                    remainingSeconds--;
                    Toast.makeText(CountDownActivity.this, getString(R.string.time_out),Toast.LENGTH_SHORT).show();
                    countDown.stop();
                    running = false;

                }
                countDown.setText(getTimeFormat(remainingSeconds));

            }
        });
        // start the count down
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running && remainingSeconds > 1) {
                    countDown.start();
                    running = true;
                }
            }
        });
        // pause the count down
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running) {
                    countDown.stop();
                    running = false;
                }
            }
        });
        // reset the count down
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remainingSeconds = originalSeconds;
                countDown.setText(getTimeFormat(remainingSeconds));
            }
        });

    }

    /**
     * get the time from the edit text fields
     */
    private void getTime() {
        int hours = getIntFromString(hoursEditText.getText().toString());
        int minutes = getIntFromString(minutesEditText.getText().toString());
        int seconds = getIntFromString(secondsEditText.getText().toString());
        if(seconds > 59 || minutes > 59 || hours > 99){
            Toast.makeText(this, getString(R.string.invaild_time),Toast.LENGTH_SHORT).show();
            return;
        }
        remainingSeconds = hours * 60 * 60 + minutes * 60 + seconds;
        originalSeconds = remainingSeconds;
        countDown.setText(getTimeFormat(remainingSeconds));
    }

    /**
     * get integer value of string or 0 if can't
     * @param s : string int
     * @return int of s or 0
     */
    private int getIntFromString(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e){
            return 0;
        }
    }

    /**
     * get the time format from remainingSeconds
     * @param seconds : the left time in remainingSeconds
     * @return time formatted
     */
    private CharSequence getTimeFormat(long seconds) {
        int hours = (int) (seconds / 60 / 60);
        int minutes = (int) (seconds / 60 % 60);
        seconds = seconds % 60;

        String timeFormat = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        return timeFormat;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(instanceState != null){
            onRestoreInstanceState(instanceState);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        instanceState = new Bundle();
        onSaveInstanceState(instanceState);
    }

    /**
     * save the values of the app
     * @param outState : bundle to store the values
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("remainingSeconds", remainingSeconds);
        outState.putBoolean("running",running);


    }

    /**
     * restore the state of the app
     * @param savedInstanceState : the saved bundle
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        remainingSeconds = savedInstanceState.getLong("remainingSeconds");
        running = savedInstanceState.getBoolean("running");
        countDown.setText(getTimeFormat(remainingSeconds));
        if(running){
            countDown.start();
        }
    }
}
