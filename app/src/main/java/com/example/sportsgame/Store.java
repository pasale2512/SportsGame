package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Store extends AppCompatActivity {
    private List<Integer> purchasedImages1; // Liste für gekaufte Bilder
    private TextView coinsTextView; // TextView zur Anzeige der Münzen
    private List<Integer> imageCoins; // Liste für Münzwerte jedes Bildes
    private int coinsCount; // Gesamtzahl der Münzen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        purchasedImages1 = loadPurchasedImages(); // Spielstand laden
        imageCoins = generateImageCoins(); // Münzwerte für jedes Bild generieren

        coinsCount = readCoinsFromTextFile(); // Münzenanzahl laden

        // Erstelle ScrollView
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT));

        // Erstelle LinearLayout als Container für ScrollView
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.END); // Verschiebe den Inhalt nach rechts

        // Erstelle GridLayout
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(6);
        gridLayout.setRowCount(7);

        // TextView für Münzen initialisieren
        coinsTextView = findViewById(R.id.coins2);
        updateCoinsDisplay(); // Münzenanzeige aktualisieren

        // Füge ImageViews hinzu
        for (int i = 1; i <= 41; i++) {
            // Erstelle LinearLayout für Bild und Text
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            itemLayout.setGravity(Gravity.CENTER);

            // Erstelle ImageView für das Bild
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getResources().getIdentifier("image" + i, "drawable", getPackageName()));
            imageView.setLayoutParams(new LayoutParams(200, 200));
            imageView.setTag(i); // Setze den Tag, um das Bild zu identifizieren
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int imageId = (int) v.getTag();
                    ImageView clickedImageView = (ImageView) v;

                    if (purchasedImages1.contains(imageId)) {
                        Toast.makeText(Store.this, "Bild bereits gekauft", Toast.LENGTH_SHORT).show();
                    } else {
                        int imageCoin = imageCoins.get(imageId - 1);
                        if (imageCoin <= coinsCount) {
                            clickedImageView.setImageResource(R.drawable.gekauft);
                            clickedImageView.setClickable(false);

                            purchasedImages1.add(imageId); // Bild zur Liste der gekauften Bilder hinzufügen
                            savePurchasedImages(purchasedImages1); // Spielstand speichern

                            coinsCount -= imageCoin; // Abziehen des Münzwerts vom Gesamtguth
                            saveCoinsToTextFile(coinsCount); // Münzenanzahl speichern
                            updateCoinsDisplay(); // Münzenanzeige aktualisieren
                        } else {
                            Toast.makeText(Store.this, "Nicht genug Münzen", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            if (purchasedImages1.contains(i)) { // Prüfe, ob das Bild bereits gekauft wurde
                imageView.setImageResource(R.drawable.gekauft);
                imageView.setClickable(false);
            }

            // Erstelle TextView für den Text
            TextView textView = new TextView(this);
            textView.setText("     Coins: " + imageCoins.get(i - 1) + "     "); // Setze den Münzwert für das Bild
            textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            // Füge ImageView und TextView zum LinearLayout hinzu
            itemLayout.addView(imageView);
            itemLayout.addView(textView);

            // Füge das LinearLayout zum GridLayout hinzu
            gridLayout.addView(itemLayout);
        }

        // Füge GridLayout zu LinearLayout hinzu
        linearLayout.addView(gridLayout);

        // Füge LinearLayout zur ScrollView hinzu
        scrollView.addView(linearLayout);

        // Setze ContentView auf ScrollView
        setContentView(scrollView);
    }

    // Spielstand laden
    private List<Integer> loadPurchasedImages() {
        List<Integer> purchasedImages1 = new ArrayList<>();
        try {
            File file = new File(getFilesDir(), "purchased_images1.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                int imageId = Integer.parseInt(line);
                purchasedImages1.add(imageId);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchasedImages1;
    }

    // Münzwerte für jedes Bild generieren
    private List<Integer> generateImageCoins() {
        List<Integer> coins = new ArrayList<>();

        for (int i = 1; i <= 41; i++) {
            coins.add(i * 10); // Setze den Münzwert basierend auf der Bildnummer
        }

        return coins;
    }

    // Spielstand speichern
    private void savePurchasedImages(List<Integer> purchasedImages1) {
        try {
            File file = new File(getFilesDir(), "purchased_images1.txt");
            FileOutputStream fos = new FileOutputStream(file);
            for (Integer imageId : purchasedImages1) {
                String line = imageId + "\n";
                fos.write(line.getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateCoinsDisplay() {
        coinsCount = readCoinsFromTextFile();
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

    private void saveCoinsToTextFile(int coinsCount) {
        try {
            File file = new File(getFilesDir(), "coins.txt");
            FileOutputStream fos = new FileOutputStream(file);
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
