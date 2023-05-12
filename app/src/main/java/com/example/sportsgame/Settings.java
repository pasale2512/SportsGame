package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Button zum Löschen der coins.txt Datei
        Button deleteCoinsButton = findViewById(R.id.deleteCoinsButton);
        deleteCoinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile("coins.txt");
                Toast.makeText(Settings.this, "Coins gelöscht", Toast.LENGTH_SHORT).show();
            }
        });

        // Button zum Löschen der purchased_images1.txt Datei
        Button deletePurchasedImagesButton = findViewById(R.id.deletePurchasedImagesButton);
        deletePurchasedImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile("purchased_images1.txt");
                Toast.makeText(Settings.this, "Einkäufe gelöscht", Toast.LENGTH_SHORT).show();
            }
        });

        // Button zum Hinzufügen von 10000 Coins in die coins.txt Datei
        Button addCoinsButton = findViewById(R.id.addCoinsButton);
        addCoinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCoinsToFile(10000);
                Toast.makeText(Settings.this, "10000 Coins hinzugefügt", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteCoinsFile(String fileName) {
        File file = new File(getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void deletePurchasedImagesFile(String fileName) {
        File file = new File(getFilesDir(), fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void addCoinsToFile(int coinsCount) {
        try {
            File file = new File(getFilesDir(), "coins.txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            for (int i = 0; i < coinsCount; i++) {
                String line = "Coin\n";
                fos.write(line.getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}