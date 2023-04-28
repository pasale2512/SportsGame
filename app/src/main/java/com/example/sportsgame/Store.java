package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class Store extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);


        //Big Pic
        ImageView imageView0 = findViewById(R.id.imageView2);
        imageView0.setImageResource(R.drawable.shopitem);

        //Player
        ImageView imageView10 = findViewById(R.id.imageView4);
        imageView10.setImageResource(R.drawable.shopitem);
        ImageView imageView11 = findViewById(R.id.imageView5);
        imageView11.setImageResource(R.drawable.shopitem);
        ImageView imageView12 = findViewById(R.id.imageView6);
        imageView12.setImageResource(R.drawable.shopitem);
        ImageView imageView13 = findViewById(R.id.imageView14);
        imageView13.setImageResource(R.drawable.shopitem);
        ImageView imageView14 = findViewById(R.id.imageView18);
        imageView14.setImageResource(R.drawable.shopitem);
        ImageView imageView15 = findViewById(R.id.imageView21);
        imageView15.setImageResource(R.drawable.shopitem);

        //Shoes
        ImageView imageView20 = findViewById(R.id.imageView9);
        imageView20.setImageResource(R.drawable.shopitem);
        ImageView imageView21 = findViewById(R.id.imageView8);
        imageView21.setImageResource(R.drawable.shopitem);
        ImageView imageView22 = findViewById(R.id.imageView7);
        imageView22.setImageResource(R.drawable.shopitem);
        ImageView imageView23 = findViewById(R.id.imageView15);
        imageView23.setImageResource(R.drawable.shopitem);
        ImageView imageView24 = findViewById(R.id.imageView17);
        imageView24.setImageResource(R.drawable.shopitem);
        ImageView imageView25 = findViewById(R.id.imageView19);
        imageView25.setImageResource(R.drawable.shopitem);

        //Shirt
        ImageView imageView30 = findViewById(R.id.imageView12);
        imageView30.setImageResource(R.drawable.shop_green_shirt);
        ImageView imageView31 = findViewById(R.id.imageView11);
        imageView31.setImageResource(R.drawable.shop_blue_shirt);
        ImageView imageView32 = findViewById(R.id.imageView10);
        imageView32.setImageResource(R.drawable.shop_red_shirt);
        ImageView imageView33 = findViewById(R.id.imageView16);
        imageView33.setImageResource(R.drawable.shop_red_shirt);
        ImageView imageView34 = findViewById(R.id.imageView13);
        imageView34.setImageResource(R.drawable.shop_red_shirt);
        ImageView imageView35 = findViewById(R.id.imageView20);
        imageView35.setImageResource(R.drawable.shop_red_shirt);
    }
}