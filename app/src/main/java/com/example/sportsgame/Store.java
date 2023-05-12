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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Store extends AppCompatActivity {
    private List<Integer> purchasedImages1; // Liste für gekaufte Bilder

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        purchasedImages1 = loadPurchasedImages(); // Spielstand laden

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

        // Füge ImageViews hinzu
        for (int i = 1; i <= 41; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getResources().getIdentifier("image" + i, "drawable", getPackageName()));
            imageView.setLayoutParams(new LayoutParams(200, 200));
            imageView.setTag(i); // Setze den Tag, um das Bild zu identifizieren
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int imageId = (int) v.getTag();
                    ImageView clickedImageView = (ImageView) v;
                    clickedImageView.setImageResource(R.drawable.gekauft); // Setze das Bild "gekauft"
                    clickedImageView.setClickable(false); // Deaktiviere den Klicklistener

                    purchasedImages1.add(imageId); // Bild zur Liste der gekauften Bilder hinzufügen
                    savePurchasedImages(purchasedImages1); // Spielstand speichern
                }
            });

            if (purchasedImages1.contains(i)) { // Prüfe, ob das Bild bereits gekauft wurde
                imageView.setImageResource(R.drawable.gekauft);
                imageView.setClickable(false);
            }

            gridLayout.addView(imageView);
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
}