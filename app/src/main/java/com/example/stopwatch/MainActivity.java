package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button startButton, stopButton, holdButton;
    private TextView timeTextView;
    private boolean isRunning = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        holdButton = findViewById(R.id.holdButton);
        timeTextView = findViewById(R.id.timeTextView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold();
            }
        });
    }

    private void start() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(updateTimeRunnable, 100);
            isRunning = true;
            startButton.setText("Resume");
            stopButton.setEnabled(true);
            holdButton.setEnabled(true);
        } else {
            elapsedTime += System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimeRunnable);
            isRunning = false;
            startButton.setText("Resume");
        }
    }

    private void stop() {
        if (isRunning) {
            elapsedTime += System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimeRunnable);
            isRunning = false;
            startButton.setText("Start");
        }
    }

    private void hold() {
        if (isRunning) {
            elapsedTime += System.currentTimeMillis() - startTime;
            handler.removeCallbacks(updateTimeRunnable);
            isRunning = false;
            startButton.setText("Start");
        } else {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.postDelayed(updateTimeRunnable, 100);
            isRunning = true;
            startButton.setText("Resume");
        }
    }

    private Runnable updateTimeRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis() - startTime + elapsedTime;
            int minutes = (int) (currentTime / 60000);
            int seconds = (int) ((currentTime / 1000) % 60);
            int tenths = (int) ((currentTime / 100) % 10);

            String timeStr = String.format("%02d:%02d.%d", minutes, seconds, tenths);
            timeTextView.setText(timeStr);

            handler.postDelayed(this, 100);
        }
    };
}
