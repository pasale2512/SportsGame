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
        ImageView imageView1 = findViewById(R.id.imageView4);
        imageView1.setImageResource(R.drawable.shopitem);
        ImageView imageView2 = findViewById(R.id.imageView5);
        imageView2.setImageResource(R.drawable.shopitem);
        ImageView imageView3 = findViewById(R.id.imageView6);
        imageView3.setImageResource(R.drawable.shopitem);

        //Shoes
        ImageView imageView4 = findViewById(R.id.imageView9);
        imageView4.setImageResource(R.drawable.shopitem);
        ImageView imageView5 = findViewById(R.id.imageView8);
        imageView5.setImageResource(R.drawable.shopitem);
        ImageView imageView6 = findViewById(R.id.imageView7);
        imageView6.setImageResource(R.drawable.shopitem);

        //Shirt
        ImageView imageView7 = findViewById(R.id.imageView12);
        imageView7.setImageResource(R.drawable.shop_green_shirt);
        ImageView imageView8 = findViewById(R.id.imageView11);
        imageView8.setImageResource(R.drawable.shop_blue_shirt);
        ImageView imageView9 = findViewById(R.id.imageView10);
        imageView9.setImageResource(R.drawable.shop_red_shirt);
    }
}