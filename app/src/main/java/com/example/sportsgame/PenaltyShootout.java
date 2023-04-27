package com.example.sportsgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.Image;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class PenaltyShootout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penalty_shootout);
        ImageView background = (ImageView) this.findViewById(R.id.imageView);
        background.setOnTouchListener(touch);
    }

    @Override
    protected void onStart() {

        super.onStart();
        ImageView quadrat = (ImageView) this.findViewById(R.id.imageView3);
        moveQuadrat(quadrat);

    }

    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_UP:
                    Snackbar.make(view, x + " " + y, Snackbar.LENGTH_SHORT).show();

                    break;
            }
            return true;
        }
    };

    private void moveQuadrat (View view){

        // Get the screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

// Create a random object
        final Random random = new Random();

// Get a reference to the ImageView you want to animate
        final ImageView imageView3 = findViewById(R.id.imageView3);

// Create a value animator that will move the ImageView randomly
        ValueAnimator animator = ValueAnimator.ofFloat(100f);
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Get the current progress of the animation
                float progress = (float) animation.getAnimatedValue();

                // Calculate a random X and Y offset
                float offsetX = random.nextInt(screenWidth) - imageView3.getWidth() / 2;
                float offsetY = random.nextInt(screenHeight) - imageView3.getHeight() / 2;

                // Set the ImageView's translation
                imageView3.setTranslationX(offsetX);
                imageView3.setTranslationY(offsetY);

                // Set the ImageView's rotation
                //imageView.setRotation(360 * progress);
            }
        });

// Start the animator
        animator.start();

        /*
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 100f);
        animation.setDuration(1000);
        animation.start();
        animation.

         */
    }
}