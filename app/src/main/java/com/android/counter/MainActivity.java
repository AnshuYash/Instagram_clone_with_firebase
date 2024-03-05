package com.android.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button,btn2;
    TextView counter;
    String stop = "Stop";
    String start = "Start";
    static int button_click = -1;
    static boolean check = true;
    static int time = 0;
    static int min = 0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        counter = findViewById(R.id.counter);
        button = findViewById(R.id.start_stop);
        btn2 = findViewById(R.id.reset);
        button.setBackgroundColor(Color.parseColor("#1D5D9B"));
        btn2.setBackgroundColor(Color.parseColor("#1D5D9B"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_click++;
                if(button_click % 2 == 0)
                {
                    counter.setText("00 : 00");
                    time = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setBackgroundColor(Color.parseColor("#FF0060"));
                        }
                    });
                    button.setText(stop);
                    check = true;
                    Thread th = new Thread(() -> {
                        for(;;)
                        {
                            if (check)
                            {
                                try{
                                    Thread.sleep(1000);
                                }
                                catch(Exception e){
                                    //to do
                                  }time++;
                                if(time > 0)
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(time <10)
                                            counter.setText(String.format("00 : 0%s", time));
                                            else{
                                                counter.setText(String.format("00 : %s", time));
                                            }
                                        }
                                    });

                                }
                                if (time >60){
                                    min = time/60;
                                    time =0;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            counter.setText(String.format("%s : %s", min, time));
                                        }
                                    });
                                }

                            }
                            else{
                                break;
                            }
                        }
                    });
                    th.start();
                }
                else{
                    check = false;
                    time--;
                    button.setBackgroundColor(Color.parseColor("#1D5D9B"));
                    Toast.makeText(MainActivity.this,"Time stopped at: " + counter.getText().toString(),Toast.LENGTH_LONG).show();
                    button.setText(start);
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = false;
                time = -1;
                counter.setText("00 : 00");
                button.setBackgroundColor(Color.parseColor("#1D5D9B"));
                button.setText(start);
            }
        });
    }
}