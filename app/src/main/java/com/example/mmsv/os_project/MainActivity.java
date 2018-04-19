package com.example.mmsv.os_project;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends Activity {

    Button buttonStart,buttonStop;
    TextView textInfoProducer, textInfoConsumer;

    String infoMsgProducer;
    String infoMsgConsumer;

    boolean stop=false;
    ShareClass shareObj = new ShareClass();
    long startingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart = (Button) findViewById(R.id.buttonstart);
        textInfoProducer = (TextView) findViewById(R.id.infoproducer);
        textInfoConsumer = (TextView) findViewById(R.id.infoconsumer);
        buttonStop=(Button)findViewById(R.id.buttonstop);
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                infoMsgProducer = "Producer\n";
                infoMsgConsumer = "Consumer\n";
                stop=false;

                Thread threadProducer = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        int ele = 0;
                        Random random = new Random();

                        while (true && stop==false) {
                            String strEle = String.valueOf(ele);
                            infoMsgProducer +="produced"+" "+ strEle + "\n";

                            MainActivity.this.runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    textInfoProducer.setText(infoMsgProducer);
                                }
                            });

                            shareObj.produce(String.valueOf(ele));
                            long randomDelay = 500 + random.nextInt(1000);

                            try {
                                Thread.sleep(randomDelay);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            ele++;
                        }
                    }
                });

                Thread threadConsumer = new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while (true && stop==false) {
                            Random random = new Random();

                            while (true) {
                                infoMsgConsumer +="consumed"+"\t"+ shareObj.consume() + "\n";

                                MainActivity.this.runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        textInfoConsumer.setText(infoMsgConsumer);
                                    }});

                                long randomDelay = 500 + random.nextInt(1000);

                                try {
                                    Thread.sleep(randomDelay);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

                startingTime = System.currentTimeMillis();
                threadProducer.start();
                threadConsumer.start();
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop=true;
            }
        });

    }
}


