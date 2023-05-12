package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView coinsTextView; // TextView zur Anzeige der Münzen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TextView für Münzen initialisieren
        coinsTextView = findViewById(R.id.coins);
        updateCoinsDisplay(); // Münzenanzeige aktualisieren

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

    // Methode zur Aktualisierung der Münzenanzeige
    private void updateCoinsDisplay() {
        int coinsCount = readCoinsFromTextFile();
        coinsTextView.setText("Coins: " + coinsCount);
    }

    // Methode zum Auslesen der Münzen aus der Textdatei
    private int readCoinsFromTextFile() {
        int coinsCount = 0;
        try {
            File file = new File(getFilesDir(), "coins.txt");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                coinsCount = Integer.parseInt(line);
                br.close();
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return coinsCount;
    }
}