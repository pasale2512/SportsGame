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
        ImageView imageView00 = findViewById(R.id.imageView00);
        imageView00.setImageResource(R.drawable.shopitem);

        //Player
        ImageView imageView10 = findViewById(R.id.imageView10);
        imageView10.setImageResource(R.drawable.shop_black_player);
        ImageView imageView11 = findViewById(R.id.imageView11);
        imageView11.setImageResource(R.drawable.shop_brown_player);
        ImageView imageView12 = findViewById(R.id.imageView12);
        imageView12.setImageResource(R.drawable.shop_normal_player);
        ImageView imageView13 = findViewById(R.id.imageView13);
        imageView13.setImageResource(R.drawable.shop_white_player);
        ImageView imageView14 = findViewById(R.id.imageView14);
        imageView14.setImageResource(R.drawable.shop_yellow_player);
        ImageView imageView15 = findViewById(R.id.imageView15);
        imageView15.setImageResource(R.drawable.shop_tomato_player);


        //Shirts
        ImageView imageView20 = findViewById(R.id.imageView20);
        imageView20.setImageResource(R.drawable.shop_blue_shirt);
        ImageView imageView21 = findViewById(R.id.imageView21);
        imageView21.setImageResource(R.drawable.shop_green_shirt);
        ImageView imageView22 = findViewById(R.id.imageView22);
        imageView22.setImageResource(R.drawable.shop_grey_shirt);
        ImageView imageView23 = findViewById(R.id.imageView23);
        imageView23.setImageResource(R.drawable.shop_white_shirt);
        ImageView imageView24 = findViewById(R.id.imageView24);
        imageView24.setImageResource(R.drawable.shop_yellow_shirt);
        ImageView imageView25 = findViewById(R.id.imageView25);
        imageView25.setImageResource(R.drawable.shop_red_shirt);


        //Shorts
        ImageView imageView30 = findViewById(R.id.imageView30);
        imageView30.setImageResource(R.drawable.shop_blue_shorts);
        ImageView imageView31 = findViewById(R.id.imageView31);
        imageView31.setImageResource(R.drawable.shop_green_shorts);
        ImageView imageView32 = findViewById(R.id.imageView32);
        imageView32.setImageResource(R.drawable.shop_grey_shorts);
        ImageView imageView33 = findViewById(R.id.imageView33);
        imageView33.setImageResource(R.drawable.shop_white_shorts);
        ImageView imageView34 = findViewById(R.id.imageView34);
        imageView34.setImageResource(R.drawable.shop_yellow_shorts);
        ImageView imageView35 = findViewById(R.id.imageView35);
        imageView35.setImageResource(R.drawable.shop_red_shorts);



    }
}