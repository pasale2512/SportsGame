package com.example.sportsgame;

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


        //Start game
        Button buttonPlay = findViewById(R.id.button);
        buttonPlay.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PenaltyShootout.class);
            startActivity(intent);
        });

        //Access the store
        Button buttonStore = findViewById(R.id.button2);
        buttonStore.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Store.class);
            startActivity(intent);
        });

        //Access the help menu
        Button buttonHelp = findViewById(R.id.button3);
        buttonHelp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Help.class);
            startActivity(intent);
        });
    }
}