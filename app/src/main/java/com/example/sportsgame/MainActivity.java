package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

        //Settings
        Button buttonHelp = findViewById(R.id.button3);
        buttonHelp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        });
    }

    private void updateCoinsDisplay() {
        int coinsCount = readCoinsFromTextFile();
        coinsTextView.setText("Coins: " + coinsCount);
    }

    private int readCoinsFromTextFile() {
        try {
            File file = new File(getFilesDir(), "coins.txt");
            if (!file.exists()) {
                file.createNewFile();
                return 0;
            }

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int coinsCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("Coin")) {
                    coinsCount++;
                }
            }
            bufferedReader.close();
            return coinsCount;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}